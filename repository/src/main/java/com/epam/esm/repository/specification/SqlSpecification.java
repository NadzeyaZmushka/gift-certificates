package com.epam.esm.repository.specification;

public interface SqlSpecification {

    String toSql();

    Object[] getParameters();

    //todo: moved to abstract class as protected
    default String getBaseStatement() {
        return "SELECT id, name FROM gift_certificates.tag ";
    }

}
