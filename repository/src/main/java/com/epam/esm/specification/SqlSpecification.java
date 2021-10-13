package com.epam.esm.specification;

public interface SqlSpecification {

    String toSql();

    Object[] getParameters();

}
