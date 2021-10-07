package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

public interface CertificateService extends EntityService<CertificateDTO> {

    void addTagToCertificate(Long certificateId, TagDTO tag);

    void deleteTagFromCertificate(Long certificateId, TagDTO tag);

    CertificateDTO update(CertificateDTO entity);

}
