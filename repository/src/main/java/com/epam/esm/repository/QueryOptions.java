package com.epam.esm.repository;

import lombok.Data;

import java.util.Map;

@Data
public class QueryOptions {

    private Integer limit;
    private Integer offset;

    private Map<String, Ordering> order;


    public enum Ordering {
        ASC, DESC
    }
}
