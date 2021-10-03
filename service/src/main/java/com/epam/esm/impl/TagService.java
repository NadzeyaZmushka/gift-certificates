package com.epam.esm.impl;

import com.epam.esm.EntityService;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.specification.SqlSpecification;
import com.epam.esm.repository.specification.impl.TagAllSpecification;
import com.epam.esm.repository.specification.impl.TagByIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements EntityService<Tag> {

    private final TagRepositoryImpl tagRepository;
    private SqlSpecification specification;

    @Autowired
    public TagService(TagRepositoryImpl tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag add(Tag entity) {
        tagRepository.add(entity);
        return entity;
    }

    @Override
    public List<Tag> findAll() {
        specification = new TagAllSpecification();
        return tagRepository.queryForList(specification);
    }

    @Override
    public Tag findById(long id) {
        specification = new TagByIdSpecification(id);
        return tagRepository.queryForOne(specification);
    }

    @Override
    public boolean delete(Tag entity) {
        return tagRepository.remove(entity);
    }
}
