package com.epam.esm.repository;

import lombok.Getter;

@Getter
public enum CertificateOrderOptions {
    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    PRICE("price"),
    DURATION("duration"),
    CREATE_DATE("createDate"),
    LAST_UPDATE_DATE("lastUpdateDate");

    private final String orderBy;

    CertificateOrderOptions(String orderBy) {
        this.orderBy = orderBy;
    }

}
