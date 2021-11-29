package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag> {

    /**
     * Find tag by its name
     *
     * @param name tag name
     * @return optional of tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Find several tag by several names
     *
     * @param tagNames list of tag names
     * @return optional of tag
     */
    List<Tag> findByNames(List<String> tagNames);

    /**
     * Find tag by certificate id
     *
     * @param id certificate id
     * @return list of tags
     */
    List<Tag> findByCertificateId(Long id);

    /**
     * Find widely used tag
     *
     * @return optional of tag
     * @param page
     * @param pageSize
     */
    List<Tag> findMostPopularTag(int page, int pageSize);

}
