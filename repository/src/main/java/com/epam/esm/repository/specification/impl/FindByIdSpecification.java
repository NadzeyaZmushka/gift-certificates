package com.epam.esm.repository.specification.impl;

public class FindByIdSpecification extends EntitySpecification {

    private final Long id;

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
        return "WHERE id = ?";
    }
}
