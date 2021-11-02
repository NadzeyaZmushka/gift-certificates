package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.BaseCrudRepository;
import com.epam.esm.repository.QueryOptions;
import com.epam.esm.service.TagService;
import com.epam.esm.specification.impl.FindAllSpecification;
import com.epam.esm.specification.impl.FindByIdSpecification;
import com.epam.esm.specification.impl.tag.TagFindByCertificateIdSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNameSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNamesSpecification;
import com.epam.esm.specification.impl.tag.TagFindMostPopularSpecification;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.exception.CustomErrorCode.TAG_INCORRECT_DATA;
import static com.epam.esm.exception.CustomErrorCode.TAG_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.SUCH_TAG_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.TAG_DUPLICATE;
import static com.epam.esm.exception.ErrorMessageCodeConstant.TAG_WITH_ID_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.TAG_WITH_NAME_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final BaseCrudRepository<Tag> tagRepository;
    private final TagValidator tagValidator;
    private final Translator translator;
    private static final String TAG_TABLE = "tag";

    @Override
    public Tag add(Tag tag) {
        tagValidator.validTag(tag);
        if (tagRepository.queryForOne(new TagFindByNameSpecification(tag.getName())).isPresent()) {
            throw new DuplicateException(translator.toLocale(TAG_DUPLICATE), TAG_INCORRECT_DATA.getErrorCode());
        } else {
            return tagRepository.add(tag);
        }
    }

    @Override
    public List<Tag> findAll(int limit, int page) {
        int offset = (page - 1) * limit;
        QueryOptions options = new QueryOptions(limit, offset);
        return tagRepository.queryForList(new FindAllSpecification<>(TAG_TABLE), options);
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.queryForOne(new FindByIdSpecification<>(TAG_TABLE, id))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(TAG_WITH_ID_NOT_FOUND), id),
                        TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    public boolean delete(Long id) {
        Tag tag = findById(id);
        if (tag == null) {
            throw new NoSuchEntityException(String.format(translator.toLocale(TAG_WITH_ID_NOT_FOUND), id),
                    TAG_NOT_FOUND.getErrorCode());
        }
        return tagRepository.remove(tag);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.queryForOne(new TagFindByNameSpecification(name))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(TAG_WITH_NAME_NOT_FOUND), name),
                        TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    public Tag findByNameOrCreate(String name) {
        return tagRepository.queryForOne(new TagFindByNameSpecification(name)).orElseGet(() -> {
            Tag tag = new Tag();
            tag.setName(name);
            return add(tag);
        });
    }

    @Override
    public List<Tag> findByCertificateId(Long certificateId) {
        return tagRepository.queryForList(new TagFindByCertificateIdSpecification(certificateId));
    }

    @Override
    public List<Tag> findByNames(List<String> names) {
        return tagRepository.queryForList(new TagFindByNamesSpecification(names));
    }

    @Override
    public Tag findWidelyUsed() {
        return tagRepository.queryForOne(new TagFindMostPopularSpecification())
                .orElseThrow(() -> new NoSuchEntityException(translator.toLocale(SUCH_TAG_NOT_FOUND),
                        TAG_NOT_FOUND.getErrorCode()));
    }

}
