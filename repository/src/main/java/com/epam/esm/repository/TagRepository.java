package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag> {

    List<Tag> findTagsByGiftCertificateId(Long id);

}
