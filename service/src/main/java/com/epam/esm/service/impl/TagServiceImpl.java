package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ErrorConstants;
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

    private static final String TABLE = "tag";

    private final TagRepositoryImpl tagRepository;
    private final TagValidator tagValidator;

    @Override
    public Tag add(Tag tag) {
        tagValidator.validName(tag.getName());
        return tagRepository.add(tag);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.queryForList(new TagFindAllSpecification());
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.queryForOne(new TagFindByIdSpecification(id))
                .orElseThrow(() -> new NoSuchEntityException(ErrorConstants.NO_TAG_WITH_ID + id
                        , TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    public void delete(Long id) {
        Tag tag = findById(id);
        if (tag == null) {
            log.error(ErrorConstants.NO_TAG_WITH_ID + id);
            throw new NoSuchEntityException(ErrorConstants.NO_TAG_WITH_ID + id, TAG_NOT_FOUND.getErrorCode());
        }
        tagRepository.remove(tag);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.queryForOne(new TagFindByNameSpecification(name))
                .orElseThrow(() -> new NoSuchEntityException(ErrorConstants.NO_TAG_WITH_NAME + name
                        , TAG_NOT_FOUND.getErrorCode()));
    }

}
