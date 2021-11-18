package com.epam.esm.specification.impl.certificate;

import com.epam.esm.entity.Certificate;
import com.epam.esm.specification.BaseSqlSpecification;

/**
 * Implementation of the {@link BaseSqlSpecification}
 * Specification for query a collection of all certificates in database
 *
 * @author Nadzeya Zmushka
 */
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
