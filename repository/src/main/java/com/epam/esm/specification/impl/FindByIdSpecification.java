package com.epam.esm.specification.impl;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.specification.BaseSqlSpecification;

public class FindByIdSpecification<T extends BaseEntity> extends BaseSqlSpecification<T> {

   private final String tableName;
   private final Long id;

    public FindByIdSpecification(String tableName, Long id) {
        this.tableName = tableName;
        this.id = id;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts." + tableName;
    }

    @Override
    public String getWhereCondition() {
        return "id = ?";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{id};
    }

}
