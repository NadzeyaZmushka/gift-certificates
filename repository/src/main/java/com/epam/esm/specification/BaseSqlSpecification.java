package com.epam.esm.specification;

import com.epam.esm.entity.BaseEntity;

import java.util.Map;

import static java.util.Optional.ofNullable;

public abstract class BaseSqlSpecification<T extends BaseEntity> implements SqlSpecification<T> {

    public abstract String getBaseStatement();

    /**
     * Return parts for join statement
     *
     * @return Map of join conditions
     */
    public Map<String, String> getJoinConditions() {
        return null;
    }

    /**
     * Returns the where condition when the query will be made
     *
     * @return String condition
     */
    public String getWhereCondition() {
        return null;
    }

    @Override
    public String toSql() {
        StringBuilder query = new StringBuilder(getBaseStatement());
        query.append(" ");
        ofNullable(getJoinConditions()).ifPresent(cond -> cond.forEach(
                (key, value) -> query.append(String.format("JOIN %s ON %s ", key, value)))
        );
        ofNullable(getWhereCondition()).ifPresent(cond -> query.append("WHERE ").append(cond));

        return query.toString();
    }
}
