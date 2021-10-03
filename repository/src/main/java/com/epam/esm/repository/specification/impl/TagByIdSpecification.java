package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.SqlSpecification;

public class TagByIdSpecification implements SqlSpecification {

    private final Long id;

    public TagByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public String toSql() {
        return getBaseStatement() + "WHERE id = ?";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{id};
    }

}
