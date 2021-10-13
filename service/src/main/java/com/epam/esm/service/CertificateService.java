package com.epam.esm.service;

import com.epam.esm.dto.AddTagToCertificateDTO;
import com.epam.esm.entity.Certificate;

import java.util.List;

public interface CertificateService extends EntityService<Certificate> {

    //todo
    void addTagsToCertificate(Long certificateId, List<String> tagsNames);

    //todo
    void deleteTagFromCertificate(Long certificateId, AddTagToCertificateDTO tagToCertificateDTO);

    Certificate update(Certificate entity);

    List<Certificate> findByCriteria(String tagName, String namePart, String orderBy);

}
