package com.epam.esm.specification.impl.certificate;

import com.epam.esm.entity.Certificate;
import com.epam.esm.specification.BaseSqlSpecification;

import java.util.HashMap;
import java.util.Map;

public class CertificateByTagNameSpecification extends BaseSqlSpecification<Certificate> {

    private final String tagName;

    public CertificateByTagNameSpecification(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts.certificate";
    }

    @Override
    public Map<String, String> getJoinConditions() {
        Map<String, String> joinConditions = new HashMap<>();
        joinConditions.put("gifts.certificate_tag", "certificate.id = certificate_tag.certificate_id");
        joinConditions.put("gifts.tag", "certificate_tag.tag_id = tag.id");

        return joinConditions;
    }

    @Override
    public String getWhereCondition() {
        return "tag.name = ?";
    }


    @Override
    public Object[] getParameters() {
        return new Object[]{tagName};
    }
}
