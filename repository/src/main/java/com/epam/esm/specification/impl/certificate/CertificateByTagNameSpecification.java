package com.epam.esm.specification.impl.certificate;

import com.epam.esm.specification.BaseSpecification;

public class CertificateByTagNameSpecification extends BaseSpecification {

    private final String tagName;

    public CertificateByTagNameSpecification(String tagName) {
        super("certificate");
        this.tagName = tagName;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{tagName};
    }

    @Override
    protected String getWhereStatement() {
        return "WHERE tag.name = ?";
    }

    @Override
    protected String getJoinStatement() {
        return "JOIN gifts.certificate_tag ct ON certificate.id = ct.certificate_id " +
                "JOIN gifts.tag ON ct.tag_id = tag.id";
    }

}
