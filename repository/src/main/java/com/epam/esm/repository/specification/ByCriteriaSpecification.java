package com.epam.esm.repository.specification;

import java.util.Arrays;

public class ByCriteriaSpecification implements SqlSpecification{

//    private Integer limit;
//    private Integer offset;
    private final String tagName;
    private final String namePart;
    private final String orderBy;

    private static final String FIND_ALL_SQL = "SELECT * FROM gifts.certificate c";
    private static final String JOIN_PART = " LEFT JOIN gifts.certificate_tag ct ON c.id = ct.certificate_id " +
            "LEFT JOIN gifts.tag tag ON ct.tag_id = tag.id WHERE ";
    private static final String BY_TAG_NAME = "tag.name = ?";
    private static final String BY_PART_NAME = " c.name LIKE %";
    private static final String ORDER_BY = " ORDER BY ";


//    private Map<String, Ordering> order;

    public ByCriteriaSpecification(String tagName, String namePart, String orderBy) {
        this.tagName = tagName;
        this.namePart = namePart;
        this.orderBy = orderBy;
    }

    @Override
    public String toSql() {
        String query;
        if (tagName == null && namePart == null && !isOrderBy()) {
            return FIND_ALL_SQL;
        } else {
            query = FIND_ALL_SQL;
        }
        if (tagName != null) {
            query = query + JOIN_PART + BY_TAG_NAME;
            if (namePart != null) {
                query = query + " AND " + BY_PART_NAME;
            }
        } else {
            if (namePart != null) {
                query = query + JOIN_PART + BY_PART_NAME;
            }
        }
        if (isOrderBy()) {
            query = query + ORDER_BY + orderBy;
        }
        return query;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{tagName, namePart};
    }

    public boolean isOrderBy() {
        return Arrays.stream(OrderBy.values())
                .anyMatch(value -> value.getOrderByField().equals(orderBy));
    }

//    public enum Ordering {
//        ASC, DESC;
//
//    }

}
