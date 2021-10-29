package com.epam.esm.specification.impl.certificate;

import com.epam.esm.entity.Certificate;
import com.epam.esm.specification.BaseSqlSpecification;

public class CertificateByNameSpecification extends BaseSqlSpecification<Certificate> {

    private final String name;

    public CertificateByNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts.certificate";
    }

    @Override
    public String getWhereCondition() {
        return "name = ?";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{name};
    }

}
