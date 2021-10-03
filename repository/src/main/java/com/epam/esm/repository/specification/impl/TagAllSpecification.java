package com.epam.esm.repository.specification.impl;

import com.epam.esm.repository.specification.SqlSpecification;

//todo: findAll и findById для всех один
public class TagAllSpecification implements SqlSpecification {

    //todo: сделать, чтобы не писать каждый раз getBaseStatement()
    @Override
    public String toSql() {
        return getBaseStatement();
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
