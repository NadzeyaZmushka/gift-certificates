package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

public interface CertificateRepository extends CrudRepository<Certificate> {

    //??
    void addTagToCertificate(Long certificatedId, Tag tag);

}
