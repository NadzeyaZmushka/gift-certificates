package com.epam.esm.repository.specification;

public interface SqlSpecification {

    String toSql();

    Object[] getParameters();

}
