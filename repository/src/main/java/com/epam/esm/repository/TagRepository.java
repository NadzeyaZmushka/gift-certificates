package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag> {

    Optional<Tag> findByName(String name);

    List<Tag> findByNames(List<String> tagNames);

    List<Tag> findByCertificateId(Long id);

    Optional<Tag> findMostPopularTag();

}
