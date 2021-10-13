package com.epam.esm.specification.impl.tag;

import com.epam.esm.specification.BaseSpecification;

public class TagByCertificateIdSpecification extends BaseSpecification {

    private final Long certificateId;

    public TagByCertificateIdSpecification(Long certificateId) {
        super("tag");
        this.certificateId = certificateId;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{certificateId};
    }

    @Override
    protected String getWhereStatement() {
        return "WHERE ct.certificate_id = ?";
    }

    @Override
    protected String getJoinStatement() {
        return "JOIN gifts.certificate_tag ct ON tag.id = ct.tag_id ";
    }
}
