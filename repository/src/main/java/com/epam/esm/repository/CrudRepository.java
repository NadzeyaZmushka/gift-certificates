package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import com.epam.esm.repository.specification.SqlSpecification;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends Entity> {

    List<T> queryForList(SqlSpecification specification);

//    List<T> queryForList(SqlSpecification specification, ByCriteriaSpecification options);

    Optional<T> queryForOne(SqlSpecification specification);

    T add(T entity);

    T update(T entity);

    boolean remove(T entity);

    void addAll(List<T> tagCertificateList);

    void removeAll(List<T> tagCertificateList);
}
