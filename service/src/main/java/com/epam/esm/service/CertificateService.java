package com.epam.esm.service;

import com.epam.esm.dto.AddTagToCertificateDTO;
import com.epam.esm.dto.CertificateDTO;

import java.util.List;

public interface CertificateService extends EntityService<CertificateDTO> {

    void addTagsToCertificate(Long certificateId, AddTagToCertificateDTO tagToCertificateDTO);

    void deleteTagFromCertificate(Long certificateId, AddTagToCertificateDTO tagToCertificateDTO);

    CertificateDTO update(CertificateDTO entity);

    List<CertificateDTO> findByCriteria(String tagName, String namePart, String orderBy);

}
