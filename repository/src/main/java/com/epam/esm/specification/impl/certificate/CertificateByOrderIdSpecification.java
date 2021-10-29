package com.epam.esm.specification.impl.certificate;

import com.epam.esm.entity.Certificate;
import com.epam.esm.specification.BaseSqlSpecification;

public class CertificateByOrderIdSpecification extends BaseSqlSpecification<Certificate> {

    private final Long orderId;

    public CertificateByOrderIdSpecification(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String getBaseStatement() {
        return "SELECT * FROM gifts.certificate";
    }

//    @Override
//    public Map<String, String> getJoinConditions() {
//        Map<String, String> joinConditions = new LinkedHashMap<>();
//        joinConditions.put("gifts.order", "order.certificate_id = certificate.id");
//        return joinConditions;
//    }

    // todo ???
    @Override
    public String toSql() {
        return "SELECT * FROM gifts.certificate LEFT JOIN gifts.order ON certificate.id = \"order\".certificate_id WHERE \"order\".id = ?";
    }

//    @Override
//    public String getWhereCondition() {
//        return "order.id = ?";
//    }

    @Override
    public Object[] getParameters() {
        return new Object[]{orderId};
    }
}
