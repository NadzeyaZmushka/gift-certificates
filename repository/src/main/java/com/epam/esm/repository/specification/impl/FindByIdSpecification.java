package com.epam.esm.repository.specification.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class FindByIdSpecification extends EntitySpecification {

    private final Long id;

    public FindByIdSpecification(String tableName, Long id) {
        super(tableName);
        this.id = id;
    }

    @Override
    public String toSql() {
        return getBaseStatement() + " WHERE id = :id";
    }

    @Override
    public SqlParameterSource getParameters() {
        return new MapSqlParameterSource().addValue("id", id);
    }
}
