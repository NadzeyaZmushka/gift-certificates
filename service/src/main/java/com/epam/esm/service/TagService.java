package com.epam.esm.service;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService extends EntityService<Tag> {

    /**
     * Finds tag by its name
     *
     * @param name of tag
     * @return tag with this name
     */
    Tag findByName(String name);

    /**
     * Finds tag by its name. Saves the tag if it was not found
     *
     * @param name of tag
     * @return tag with this name(or saved tag with this name)
     */
    Tag findByNameOrCreate(String name);

    /**
     * Finds tags that belong to a certificate with this id.
     *
     * @param certificateId certificate id
     * @return list of tags that belong to the certificate
     */
    List<Tag> findByCertificateId(Long certificateId);

    /**
     * Finds tags by by multiple names
     *
     * @param names list of names
     * @return list of tags with these names
     */
    List<Tag> findByNames(List<String> names);

    /**
     * Finds widely used tag
     *
     * @return tag
     */
    List<WidelyUsedTagsByUser> findWidelyUsed();

}
