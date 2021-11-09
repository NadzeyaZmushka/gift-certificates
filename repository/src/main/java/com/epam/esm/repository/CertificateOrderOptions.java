package com.epam.esm.repository;

import lombok.Getter;

@Getter
public enum CertificateOrderOptions {
    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    PRICE("price"),
    DURATION("duration"),
    CREATE_DATE("create_date"),
    LAST_UPDATE_DATE("last_update_date");

    private final String orderBy;

    CertificateOrderOptions(String orderBy) {
        this.orderBy = orderBy;
    }

}
