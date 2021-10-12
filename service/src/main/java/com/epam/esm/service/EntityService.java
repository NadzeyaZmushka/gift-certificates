package com.epam.esm.service;

import com.epam.esm.dto.BaseEntityDTO;

import java.util.List;

public interface EntityService<T extends BaseEntityDTO> {

    T add(T entity);

    List<T> findAll();

    T findById(long id);

    void delete(Long id);

}
