package com.epam.esm.repository.specification.impl;

public class TagFindByNameSpecification extends EntitySpecification {

    private final String name;
    private static final String WHERE_NAME_STATEMENT = "WHERE name = ?";

    public TagFindByNameSpecification(String name) {
        super("tag");
        this.name = name;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{name};
    }

    @Override
    protected String getWhereStatement() {
        return WHERE_NAME_STATEMENT;
    }
}
