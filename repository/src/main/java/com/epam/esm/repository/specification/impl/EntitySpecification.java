package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.SqlSpecification;

public abstract class EntitySpecification implements SqlSpecification {

    private final String tableName;

    public EntitySpecification(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toSql() {
        return getBaseStatement() + getWhereStatement();
    }

    protected String getBaseStatement() {
        return String.format("SELECT * FROM gifts.%s ", tableName);
    }

    protected abstract String getWhereStatement();

}
