package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.repository.specification.SqlSpecification;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends BaseEntity> {

    List<T> queryForList(SqlSpecification specification);

    Optional<T> queryForOne(SqlSpecification specification);

    T add(T entity);

    boolean update(T entity);

    boolean remove(T entity);
}
