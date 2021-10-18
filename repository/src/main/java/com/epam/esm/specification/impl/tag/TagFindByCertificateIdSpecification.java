package com.epam.esm.specification.impl.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.specification.BaseSqlSpecification;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the {@link BaseSqlSpecification}
 * Specification for query a collection of tags by certificate id in database
 *
 * @author Nadzeya Zmushka
 */
public class TagFindByCertificateIdSpecification extends BaseSqlSpecification<Tag> {
    private final Long certificateId;

    public TagFindByCertificateIdSpecification(Long certificateId) {
        this.certificateId = certificateId;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts.tag";
    }

    @Override
    public Map<String, String> getJoinConditions() {
        Map<String, String> joinConditions = new HashMap<>();
        joinConditions.put("gifts.certificate_tag", "tag.id = certificate_tag.tag_id");
        return joinConditions;
    }

    @Override
    public String getWhereCondition() {
        return "certificate_tag.certificate_id = ?";
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{certificateId};
    }

}
