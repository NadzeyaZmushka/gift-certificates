package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.repository.specification.SqlSpecification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityRepository<T extends BaseEntity, S extends SqlSpecification> {

    List<T> queryForList(SqlSpecification specification);

    T queryForOne(SqlSpecification specification);

    T add(T entity);

    boolean update(T entity);

    boolean remove(T entity);
}
