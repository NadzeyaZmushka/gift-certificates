package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;

import java.util.List;

public interface AbstractRepository<T extends BaseEntity> {

    List<T> findAll();

    T findById(Long id);

    T create(T entity);

    boolean update(T entity);

    boolean delete(Long id);
}
