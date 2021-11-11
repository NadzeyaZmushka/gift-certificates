package com.epam.esm.repository;

import com.epam.esm.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Interface that defines CRUD operations with entities
 *
 * @param <T> entities which repository operates with
 * @author Nadzeya Zmushka
 */

public interface CrudRepository<T extends BaseEntity> {

    /**
     * Finds objects in database
     *
     * @param page
     * @param pageSize
     * @return list of objects
     */
    List<T> findAll(int page, int pageSize);


    /**
     * Finds one object in database
     *
     * @param id
     * @return Optional of found object
     */
    Optional<T> findById(Long id);

    /**
     * Saves object to database
     *
     * @param entity the object to be saved to database
     * @return
     */
    T add(T entity);

    /**
     * Updates object in database
     *
     * @param entity the object to be updated in database
     */
    void update(T entity);

    /**
     * Deletes object from database
     *
     * @param entity object to be deleted from database
     */
    void remove(T entity);

    Long count();

}
