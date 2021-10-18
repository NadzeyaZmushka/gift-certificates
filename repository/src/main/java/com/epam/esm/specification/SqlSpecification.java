package com.epam.esm.specification;

import com.epam.esm.entity.Entity;

/**
 * Interface for create sql clauses and parameters
 *
 * @param <T> entities which repository operates with
 * @author Nadzeya Zmushka
 */
public interface SqlSpecification<T extends Entity> {

    /**
     * Creates a query for database queries
     *
     * @return sql query
     */
    String toSql();

    /**
     * Create parameters for sql query
     *
     * @return parameters for query
     */
    Object[] getParameters();

}
