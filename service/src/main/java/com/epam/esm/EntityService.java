package com.epam.esm;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface EntityService<T extends BaseEntity> {

    T add(T entity);

    List<T> findAll();

    T findById(long id);

    boolean delete(T entity);
}
