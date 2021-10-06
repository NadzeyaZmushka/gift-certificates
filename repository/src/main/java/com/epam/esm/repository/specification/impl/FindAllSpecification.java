package com.epam.esm.repository.specification.impl;

public class FindAllSpecification extends EntitySpecification {

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
