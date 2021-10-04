package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag> {

    List<Tag> findTagsByGiftCertificateId(Long id);

    Optional<Tag> isPresent(String name);

}
