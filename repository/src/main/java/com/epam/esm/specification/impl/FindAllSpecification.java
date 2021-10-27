package com.epam.esm.specification.impl;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.specification.BaseSqlSpecification;

public class FindAllSpecification<T extends BaseEntity> extends BaseSqlSpecification<T> {

    private final String tableName;

    public FindAllSpecification(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts." + tableName;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }

}
