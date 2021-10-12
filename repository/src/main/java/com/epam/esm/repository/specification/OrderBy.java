package com.epam.esm.repository.specification;

import lombok.Getter;

@Getter
public enum OrderBy {
    NAME("name"),
    CREATION_DATE("create_date");

    private final String orderByField;

    OrderBy(String orderByField) {
        this.orderByField = orderByField;
    }
}
