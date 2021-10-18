package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService extends EntityService<Tag> {

    Tag findByName(String name);

    Tag findByNameOrCreate(String name);

    List<Tag> findByCertificateId(Long certificateId);

    List<Tag> findByNames(List<String> names);

}
