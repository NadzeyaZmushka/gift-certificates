package com.epam.esm.impl;

import com.epam.esm.EntityService;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NoSuchTagException;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.repository.specification.impl.FindAllSpecification;
import com.epam.esm.repository.specification.impl.FindByIdSpecification;
import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

//@Slf4j
@Service
@RequiredArgsConstructor
public class TagService implements EntityService<Tag> {

    private final TagRepositoryImpl tagRepository;

    @Override
    public Tag add(Tag entity) {
        tagRepository.add(entity);
        return entity;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.queryForList(new FindAllSpecification("tag"));
    }

    @Override
    public Tag findById(long id) {
        return tagRepository.queryForOne(new FindByIdSpecification("tag", id))
                .orElseThrow(()-> new NoSuchTagException("no tag with such id"));
    }

    @Override
    public boolean delete(Long id) {
        Tag tag = findById(id);
        if (tag == null) {
            throw new NoSuchTagException("There is no tag with id = " + id + " in database");
        }
        return tagRepository.remove(tag);
    }

    @Override
    public boolean update(Tag tag) {
        return tagRepository.update(tag);
    }


}
