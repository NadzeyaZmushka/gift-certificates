package com.epam.esm.specification.impl;

import com.epam.esm.entity.BaseEntity;
import com.epam.esm.specification.BaseSqlSpecification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link BaseSqlSpecification}
 * Specification for query a collection of objects by multiple criteria in database
 *
 * @param <T> entities which repository operates with
 * @author Nadzeya Zmushka
 */
public class AndSpecification<T extends BaseEntity> extends BaseSqlSpecification<T> {

    private final List<BaseSqlSpecification<T>> specifications;

    //todo do error message constants
    public AndSpecification(Collection<BaseSqlSpecification<T>> specifications) {
        if (specifications == null || specifications.size() <= 0) {
            throw new IllegalArgumentException("No criteria for searching");
        }
        this.specifications = new ArrayList<>(specifications);
    }

    @Override
    public String getBaseStatement() {
        return specifications.get(0).getBaseStatement();
    }

    @Override
    public Map<String, String> getJoinConditions() {
        Map<String, String> joins = new LinkedHashMap<>();
        for (BaseSqlSpecification<T> specification : specifications) {
            Optional.ofNullable(specification.getJoinConditions())
                    .ifPresent(cond -> cond.forEach(joins::put));
        }
        return joins;
    }

    @Override
    public String getWhereCondition() {
        return specifications.stream()
                .map(BaseSqlSpecification::getWhereCondition)
                .collect(Collectors.joining(" AND "));
    }

    @Override
    public Object[] getParameters() {
        List<Object> params = new ArrayList<>();
        specifications.forEach(spec -> Collections.addAll(params, spec.getParameters()));

        return params.toArray();
    }

}