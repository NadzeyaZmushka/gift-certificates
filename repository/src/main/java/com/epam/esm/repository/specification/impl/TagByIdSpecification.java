package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.TagSpecification;

public class TagByIdSpecification extends TagSpecification {

    private final Long id;

    public TagByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public String toSql() {
        return getBaseStatement() + "WHERE tag_id = ?";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{id};
    }

}
