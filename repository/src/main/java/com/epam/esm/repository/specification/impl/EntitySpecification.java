package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.SqlSpecification;

public abstract class EntitySpecification implements SqlSpecification {

    private final String tableName;

    public EntitySpecification(String tableName) {
        this.tableName = tableName;
    }

    protected String getBaseStatement() {
        return "SELECT * FROM gift_certificates." + tableName;
    }

}
