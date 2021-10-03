package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.repository.specification.SqlSpecification;

import java.util.List;

public interface EntityRepository<T extends BaseEntity> {

    List<T> queryForList(SqlSpecification specification);

    T queryForOne(SqlSpecification specification);

    void add(T entity);

    boolean update(T entity);

    boolean remove(T entity);
}
