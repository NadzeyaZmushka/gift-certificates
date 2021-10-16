package com.epam.esm.service;

import com.epam.esm.entity.BaseEntity;

import java.util.List;

public interface EntityService<T extends BaseEntity> {

    T add(T entity);

    List<T> findAll();

    T findById(Long id);

    void delete(Long id);

}
