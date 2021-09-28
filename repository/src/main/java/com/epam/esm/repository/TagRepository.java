package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagRepository extends AbstractRepository<Tag> {

    List<Tag> receiveTagByCertificateId(Long certificateId);

}
