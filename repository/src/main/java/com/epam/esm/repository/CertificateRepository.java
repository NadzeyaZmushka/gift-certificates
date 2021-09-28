package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface CertificateRepository extends AbstractRepository<Certificate> {

    List<Tag> findCertificateTags(Long certificateId);

}
