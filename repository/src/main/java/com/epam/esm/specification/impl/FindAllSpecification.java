package com.epam.esm.specification.impl;

import com.epam.esm.specification.BaseSqlSpecification;

public class FindAllSpecification extends BaseSqlSpecification {

    private final String tableName;

    public FindAllSpecification(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getBaseStatement() {
        return String.format("SELECT * from gifts.%s", tableName);
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
