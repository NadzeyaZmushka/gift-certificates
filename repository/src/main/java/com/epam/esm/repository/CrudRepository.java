package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import com.epam.esm.specification.SqlSpecification;

import java.util.List;
import java.util.Optional;

/**
 *
 * @param <T>
 */

public interface CrudRepository<T extends Entity> {

    List<T> queryForList(SqlSpecification<T> specification);

    List<T> queryForList(SqlSpecification<T> specification, QueryOptions options);

    Optional<T> queryForOne(SqlSpecification<T> specification);

    T add(T entity);

    T update(T entity);

    boolean remove(T entity);

    void addAll(List<T> tagCertificateList);

    void removeAll(List<T> tagCertificateList);
}
