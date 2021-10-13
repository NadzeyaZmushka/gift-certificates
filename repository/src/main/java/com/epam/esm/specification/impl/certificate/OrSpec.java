package com.epam.esm.specification.impl.certificate;

import com.epam.esm.specification.SqlSpecification;

public class OrSpec implements SqlSpecification {

    public OrSpec(SqlSpecification... specifications) {

    }

    @Override
    public String toSql() {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
