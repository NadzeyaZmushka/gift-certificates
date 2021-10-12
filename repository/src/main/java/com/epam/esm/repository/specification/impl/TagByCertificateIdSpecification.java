package com.epam.esm.repository.specification.impl;

public class TagByCertificateIdSpecification extends EntitySpecification{

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
        return "JOIN gifts.certificate_tag ct ON tag.id = ct.tag_id WHERE ct.certificate_id = ?";
    }
}
