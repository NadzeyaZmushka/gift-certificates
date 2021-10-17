package com.epam.esm.repository;

import com.epam.esm.entity.Entity;
import com.epam.esm.specification.SqlSpecification;

import java.util.List;
import java.util.Optional;

/**
 * Interface that defines CRUD operations with entities
 *
 * @param <T> entities which repository operates with
 * @author Nadzeya Zmushka
 * @see BaseCrudRepository
 */

public interface CrudRepository<T extends Entity> {

    /**
     * Finds objects in database
     *
     * @param specification with sql and parameters
     * @return list of objects
     */
    List<T> queryForList(SqlSpecification<T> specification);

    /**
     * Finds objects in database with parameters and ordering
     *
     * @param specification with sql and parameters
     * @param options       ordering by
     * @return list of objects
     */
    List<T> queryForList(SqlSpecification<T> specification, QueryOptions options);

    /**
     * Finds one object in database
     *
     * @param specification with sql and parameters
     * @return Optional of found object
     */
    Optional<T> queryForOne(SqlSpecification<T> specification);

    /**
     * Saves object to database
     *
     * @param entity the object to be saved to database
     * @return saved object
     */
    T add(T entity);

    /**
     * Updates object in database
     *
     * @param entity the object to be updated in database
     * @return updated object
     */
    T update(T entity);

    /**
     * Deletes object from database
     *
     * @param entity object to be deleted from database
     * @return true if deleted
     */
    boolean remove(T entity);

    /**
     * Adds tags to certificate
     *
     * @param tagCertificateList list of tags to certificate
     */
    void addAll(List<T> tagCertificateList);

    /**
     * Deletes tags from certificate
     *
     * @param tagCertificateList list of tags to certificate
     */
    void removeAll(List<T> tagCertificateList);

}
