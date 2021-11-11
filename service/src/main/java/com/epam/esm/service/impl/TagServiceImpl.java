package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.exception.CustomErrorCode.TAG_INCORRECT_DATA;
import static com.epam.esm.exception.CustomErrorCode.TAG_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.SUCH_TAG_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.TAG_DUPLICATE;
import static com.epam.esm.exception.ErrorMessageCodeConstant.TAG_WITH_ID_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.TAG_WITH_NAME_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagValidator tagValidator;
    private final Translator translator;
    private static final String TAG_TABLE = "tag";

    @Override
    @Transactional
    public Tag add(Tag tag) {
        tagValidator.validTag(tag);
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new DuplicateException(translator.toLocale(TAG_DUPLICATE), TAG_INCORRECT_DATA.getErrorCode());
        } else {
            return tagRepository.add(tag);
        }
    }

    @Override
    @Transactional
    public List<Tag> findAll(int limit, int page) {
        return tagRepository.findAll(page, limit);
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(TAG_WITH_ID_NOT_FOUND), id),
                        TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag tag = findById(id);
        if (tag == null) {
            throw new NoSuchEntityException(String.format(translator.toLocale(TAG_WITH_ID_NOT_FOUND), id),
                    TAG_NOT_FOUND.getErrorCode());
        }
        tagRepository.remove(tag);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(TAG_WITH_NAME_NOT_FOUND), name),
                        TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    @Transactional
    public Tag findByNameOrCreate(String name) {
        return tagRepository.findByName(name).orElseGet(() -> {
            Tag tag = new Tag();
            tag.setName(name);
            add(tag);
            return tag;
        });
    }

    @Override
    public List<Tag> findByCertificateId(Long certificateId) {
        return tagRepository.findByCertificateId(certificateId);
    }

    @Override
    public List<Tag> findByNames(List<String> names) {
        return tagRepository.findByNames(names);
    }

    @Override
    public Tag findWidelyUsed() {
        return tagRepository.findMostPopularTag()
                .orElseThrow(() -> new NoSuchEntityException(translator.toLocale(SUCH_TAG_NOT_FOUND),
                        TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    public Long count() {
        return tagRepository.count();
    }

}
