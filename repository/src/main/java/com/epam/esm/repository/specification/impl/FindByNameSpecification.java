package com.epam.esm.repository.specification.impl;

public class FindByNameSpecification extends EntitySpecification {

    private final String name;

    public FindByNameSpecification(String tableName, String name) {
        super(tableName);
        this.name = name;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{name};
    }

    @Override
    protected String getWhereStatement() {
        return "WHERE name = ?";
    }
}
