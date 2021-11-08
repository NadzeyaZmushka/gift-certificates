package com.epam.esm.repository.impl;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Class with options and ordering for query
 *
 * @author Nadzeya Zmushka
 */
@Data
@NoArgsConstructor
public class QueryOptions {

    private Integer limit;
    private Integer offset;

    private Map<String, Ordering> order;

    public QueryOptions(Integer limit, Integer offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public enum Ordering {
        ASC, DESC
    }

}
