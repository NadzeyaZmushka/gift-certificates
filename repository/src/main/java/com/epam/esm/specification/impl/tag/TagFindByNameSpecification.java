package com.epam.esm.specification.impl.tag;

import com.epam.esm.specification.BaseSpecification;

public class TagFindByNameSpecification extends BaseSpecification {

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
