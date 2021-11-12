package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.repository.CertificateOrderOptions;
import com.epam.esm.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {

    private final EntityManager entityManager;

    private static final String FIND_ALL_QUERY = "select c from Certificate c";
    private static final String FIND_BY_NAME_QUERY = "select c from Certificate c where c.name =: name";
    private static final String COUNT_QUERY = "select count(c) from Certificate c";

    @Override
    public Certificate add(Certificate entity) {
        entity.setCreateDate(LocalDateTime.now());
        entity.setLastUpdateDate(LocalDateTime.now());
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public List<Certificate> findAll(int page, int pageSize) {
        return entityManager.createQuery(FIND_ALL_QUERY, Certificate.class)
                .setFirstResult(pageSize * (page - 1))
                .setMaxResults(pageSize)
                .getResultList();
    }

    public List<Certificate> findAll(List<String> tagNames, String namePart, String orderBy, String order,
                                     int page, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> query = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = query.from(Certificate.class);
        query.select(root);

        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isBlank(namePart)) {
            Predicate predicateForName = criteriaBuilder.like(root.get("name"), "%" + namePart + "%");
            predicates.add(predicateForName);
        }
        if (tagNames != null && tagNames.size() != 0) {
            Join<Object, Object> tagListJoin = root.join("tags");
            Expression<Long> countOfTagsInGroup = criteriaBuilder.count(root);
            Predicate predicateTagsList = tagListJoin.get("name").in(tagNames);
            predicates.add(predicateTagsList);
            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                    .having(criteriaBuilder.equal(countOfTagsInGroup, tagNames.size()))
                    .groupBy(root);
        } else {
            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }
        if (Arrays.stream(CertificateOrderOptions.values())
                .anyMatch(value -> value.getOrderBy().equals(orderBy))) {
            if (order.equalsIgnoreCase("asc")) {
                query.orderBy(criteriaBuilder.asc(root.get(orderBy)));
            }
            if (order.equalsIgnoreCase("desc")) {
                query.orderBy(criteriaBuilder.desc(root.get(orderBy)));
            }
        }
        return entityManager.createQuery(query)
                .setFirstResult(pageSize * (page - 1))
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    public Optional<Certificate> findByName(String name) {
        try {
            return Optional.of(entityManager.createQuery(FIND_BY_NAME_QUERY, Certificate.class)
                    .setParameter("name", name)
                    .setMaxResults(1)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Certificate entity) {
        entity.setLastUpdateDate(LocalDateTime.now());
        entityManager.merge(entity);
    }

    @Override
    public void remove(Certificate entity) {
        entityManager.remove(entity);
    }

    @Override
    public Long count() {
        return (Long) entityManager.createQuery(COUNT_QUERY)
                .getSingleResult();
    }

}
