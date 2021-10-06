package com.epam.esm.service;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

public interface CertificateService extends EntityService<Certificate> {

    void addTagToCertificate(Long certificateId, Tag tag);

    void deleteTagFromCertificate(Long certificateId, Tag tag);

    Certificate update(Certificate entity);

}
