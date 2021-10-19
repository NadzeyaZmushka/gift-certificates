package com.epam.esm.service;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateService extends EntityService<Certificate> {

    /**
     * Finds certificates that matches passed parameters
     *
     * @param tagName  tag name
     * @param partName part of certificate name
     * @param sortBy   field by which to sort
     * @param order    in what order to sort
     * @return list of certificates
     */
    List<Certificate> findAllByCriteria(String tagName, String partName, String sortBy, String order);

    /**
     * Updates certificate that has such id
     *
     * @param entity that need to be update
     * @return updated certificate
     */
    Certificate update(Certificate entity);

    /**
     * Adds tags to certificate
     *
     * @param certificateId id of certificate that need to be found to add tags
     * @param tagsNames     list of tag names that need to be added
     */
    void addTagsToCertificate(Long certificateId, List<String> tagsNames);

    /**
     * Deletes tags from certificate
     *
     * @param certificateId id of certificate that need to be found to delete tags
     * @param tagsNames     list of tag names that need to be deleted
     */
    void deleteTagFromCertificate(Long certificateId, List<String> tagsNames);

}
