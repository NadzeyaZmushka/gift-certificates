package com.epam.esm.service;

import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateService extends EntityService<Certificate> {

    void addTagsToCertificate(Long certificateId, List<String> tagsNames);

    void deleteTagFromCertificate(Long certificateId, List<String> tagsNames);

    Certificate update(Certificate entity);

    List<Certificate> findByCriteria(String tagName, String partName, String sortBy, String order);

}
