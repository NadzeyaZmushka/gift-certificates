package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends CrudRepository<Certificate> {

    /**
     * Find certificates in database by criteria
     *
     * @param tagNames tags names
     * @param name     certificate name or part of name
     * @param orderBy  order by
     * @param order    ordering(asc, desc)
     * @param page     page
     * @param pageSize limit
     * @return list of certificates
     */
    List<Certificate> findAll(List<String> tagNames, String name, String orderBy, String order,
                              int page, int pageSize);

    /**
     * Find certificate by its name
     *
     * @param name certificate name
     * @return optional of certificate
     */
    Optional<Certificate> findByName(String name);

}
