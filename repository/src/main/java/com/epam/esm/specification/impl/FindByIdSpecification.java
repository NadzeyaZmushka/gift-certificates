package com.epam.esm.specification.impl;

import com.epam.esm.specification.BaseSqlSpecification;

public class FindByIdSpecification extends BaseSqlSpecification {

    private final String tableName;
    private final Long id;

    public FindByIdSpecification(String tableName, Long id) {
        this.tableName = tableName;
        this.id = id;
    }

    @Override
    public String getBaseStatement() {
        return String.format("SELECT * from gifts.%s", tableName);
    }

    @Override
    public String getWhereCondition() {
        return "id = ?";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{id};
    }
}
