package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends CrudRepository<Certificate> {

    List<Certificate> findAll(List<String> tagNames, String namePart, String orderBy, String order,
                              int page, int pageSize);

    Optional<Certificate> findByName(String name);

}
