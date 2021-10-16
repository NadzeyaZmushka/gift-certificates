package com.epam.esm.service;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateService extends EntityService<Certificate> {

    List<Certificate> findAllByCriteria(String tagName, String partName, String sortBy, String order);

    Certificate update(Certificate entity);

    void addTagsToCertificate(Long certificateId, List<String> tagsNames);

    void deleteTagFromCertificate(Long certificateId, List<String> tagsNames);

}
