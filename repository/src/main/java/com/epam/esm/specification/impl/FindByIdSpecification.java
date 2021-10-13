package com.epam.esm.specification.impl;

import com.epam.esm.specification.BaseSpecification;

public class FindByIdSpecification extends BaseSpecification {

    private final Long id;
    private static final String WHERE_ID_STATEMENT = "WHERE id = ?";

    public FindByIdSpecification(String tableName, Long id) {
        super(tableName);
        this.id = id;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{id};
    }

    @Override
    protected String getWhereStatement() {
        return WHERE_ID_STATEMENT;
    }
}
