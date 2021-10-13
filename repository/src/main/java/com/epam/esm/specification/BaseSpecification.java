package com.epam.esm.specification;

import com.epam.esm.specification.SqlSpecification;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BaseSpecification implements SqlSpecification {

    private final String tableName;
    private static final String SELECT_ALL = "SELECT * FROM gifts.%s ";

    public BaseSpecification(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toSql() {
        return Stream.of(getBaseStatement(), getJoinStatement(), " ", getWhereStatement())
                .filter(Objects::nonNull)
                .collect(Collectors.joining());

    }

    private String getBaseStatement() {
        return String.format(SELECT_ALL, tableName);
    }

    protected abstract String getWhereStatement();

    protected String getJoinStatement(){
        return null;
    }

}
