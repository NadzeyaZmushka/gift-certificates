package com.epam.esm;

import com.epam.esm.entity.BaseEntity;

import java.util.List;

public interface EntityService<T extends BaseEntity> {

    T add(T entity);

    List<T> findAll();

    T findById(long id);

    boolean delete(Long id);

    boolean update(T entity);
}
