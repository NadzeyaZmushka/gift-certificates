package com.epam.esm.specification.impl.certificate;

import com.epam.esm.entity.Certificate;
import com.epam.esm.specification.BaseSqlSpecification;

import java.util.LinkedHashMap;
import java.util.Map;

public class CertificateFindAllSpecification extends BaseSqlSpecification<Certificate> {

    @Override
    public String getBaseStatement() {
        return "SELECT * " +
                "FROM gifts.certificate";
    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }
}
