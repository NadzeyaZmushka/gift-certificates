package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.repository.specification.SqlSpecification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntityRepository<T extends BaseEntity> {

    List<T> query(SqlSpecification specification);

    List<T> findAll();

    Optional<T> findById(Long id);

    T add(T entity);

    boolean update(T entity);

    boolean delete(Long id);
}
