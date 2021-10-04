package com.epam.esm.repository.specification.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class FindAllSpecification extends EntitySpecification {

    public FindAllSpecification(String tableName) {
        super(tableName);
    }

    @Override
    public String toSql() {
        return getBaseStatement();
    }

    @Override
    public SqlParameterSource getParameters() {
        return new MapSqlParameterSource();
    }
}
