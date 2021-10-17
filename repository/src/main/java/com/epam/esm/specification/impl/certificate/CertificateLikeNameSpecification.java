package com.epam.esm.specification.impl.certificate;

import com.epam.esm.entity.Certificate;
import com.epam.esm.specification.BaseSqlSpecification;

public class CertificateLikeNameSpecification extends BaseSqlSpecification<Certificate> {

    private final String partName;

    public CertificateLikeNameSpecification(String partName) {
        this.partName = partName;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts.certificate";
    }

    @Override
    public String getWhereCondition() {
        return "certificate.name LIKE concat ('%', ?, '%')";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{partName};
    }
}