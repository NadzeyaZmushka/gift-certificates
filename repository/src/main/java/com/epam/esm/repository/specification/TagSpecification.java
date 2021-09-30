package com.epam.esm.repository.specification;

public class TagSpecification implements SqlSpecification {

    private static final String SELECT_TAGS_SQL = "SELECT tag_id, tag_name FROM gift_certificates.tag";

    @Override
    public String toSql() {
        return null;
    }
}
