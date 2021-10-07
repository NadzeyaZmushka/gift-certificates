package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CustomErrorCode;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.specification.impl.FindAllSpecification;
import com.epam.esm.repository.specification.impl.FindByIdSpecification;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepositoryImpl tagRepository;
    private final TagValidator tagValidator;

    @Override
    public Tag add(Tag tag) {
        if (tagValidator.validName(tag.getName())) {
            tagRepository.add(tag);
        }
        return tag;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.queryForList(new FindAllSpecification("tag"));
    }

    @Override
    public Tag findById(long id) {
        return tagRepository.queryForOne(new FindByIdSpecification("tag", id))
                .orElseThrow(() -> new NoSuchEntityException("No tag with id = " + id, CustomErrorCode.TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    public void delete(Long id) {
        Tag tag = findById(id);
        if (tag == null) {
            log.error("There is no tag with id = " + id);
            throw new NoSuchEntityException("There is no tag with id = " + id + " in database", CustomErrorCode.TAG_NOT_FOUND.getErrorCode());
        }
        tagRepository.remove(tag);
    }

}
