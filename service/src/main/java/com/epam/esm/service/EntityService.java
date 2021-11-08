package com.epam.esm.service;

import java.util.List;

/**
 * Provides database entity info transfer between web and persistence module
 *
 * @param <T> entities which service operates with
 */
public interface EntityService<T> {

    T add(T entity);

    /**
     * Finds all entities
     *
     * @return list of certificate. May return empty list if there is no entities
     * @param limit
     * @param page
     */
    List<T> findAll(int limit, int page);

    /**
     * Finds entity that has such id
     *
     * @param id of entity that need to be found
     * @return entity with this id
     */
    T findById(Long id);

    /**
     * Deletes entities.
     *
     * @param id of entity that need to be deleted
     */
    void delete(Long id);

}
