package com.epam.esm.service;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateService extends EntityService<Certificate> {

    /**
     * Finds certificates that matches passed parameters
     *
     * @param tagNames tags names
     * @param partName part of certificate name
     * @param sortBy   field by which to sort
     * @param order    in what order to sort
     * @param limit    limit
     * @param page     page
     * @return list of certificates
     */
    List<Certificate> findAllByCriteria(List<String> tagNames, String partName, String sortBy, String order,
                                        int limit, int page);

    /**
     * Updates certificate that has such id
     *
     * @param id     certificate id
     * @param entity that need to be update
     * @return updated certificate
     */
    Certificate update(Long id, Certificate entity);

    /**
     * Adds tags to certificate
     *
     * @param certificateId id of certificate that need to be found to add tags
     * @param tagsNames     list of tag names that need to be added
     */
    void addTagsToCertificate(Long certificateId, List<String> tagsNames);

    /**
     * Finds certificate with such name
     *
     * @param name certificate name
     * @return certificate
     */
    Certificate findByName(String name);


}
