package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.specification.impl.tag.TagFindAllSpecification;
import com.epam.esm.specification.impl.tag.TagFindByIdSpecification;
import com.epam.esm.specification.impl.tag.TagFindByNameSpecification;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.exception.CustomErrorCode.TAG_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepositoryImpl tagRepository;
    private final TagValidator tagValidator;
    private final Translator translator;

    @Override
    public Tag add(Tag tag) {
        tagValidator.validTag(tag);
        return tagRepository.add(tag);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.queryForList(new TagFindAllSpecification());
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.queryForOne(new TagFindByIdSpecification(id))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale("tag.withIdNotFound"), id),
                        TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    public boolean delete(Long id) {
        Tag tag = findById(id);
        if (tag == null) {
            throw new NoSuchEntityException(String.format(translator.toLocale("tag.withIdNotFound"), id),
                    TAG_NOT_FOUND.getErrorCode());
        }
        return tagRepository.remove(tag);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.queryForOne(new TagFindByNameSpecification(name))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale("tag.withNameNotFound"), name),
                        TAG_NOT_FOUND.getErrorCode()));
    }

}
