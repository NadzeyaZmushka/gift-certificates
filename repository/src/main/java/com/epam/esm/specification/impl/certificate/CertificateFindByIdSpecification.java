package com.epam.esm.specification.impl.certificate;

import com.epam.esm.entity.Certificate;
import com.epam.esm.specification.BaseSqlSpecification;

/**
 * Implementation of the {@link BaseSqlSpecification}
 * Specification for query a certificate by its id
 *
 * @author Nadzeya Zmushka
 */
public class CertificateFindByIdSpecification extends BaseSqlSpecification<Certificate> {

    private final Long id;

    public CertificateFindByIdSpecification(Long id) {
        this.id = id;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts.certificate ";
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
