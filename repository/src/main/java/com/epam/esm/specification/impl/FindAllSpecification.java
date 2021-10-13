package com.epam.esm.specification.impl;

import com.epam.esm.specification.BaseSpecification;

public class FindAllSpecification extends BaseSpecification {

    public FindAllSpecification(String tableName) {
        super(tableName);
    }

    @Override
    protected String getWhereStatement() {
        return "";
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
