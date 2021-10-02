package com.epam.esm.repository.specification;

public abstract class TagSpecification implements SqlSpecification {

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gift_certificates.tag ";
    }
}
