package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.repository.CertificateOrderOptions;
import com.epam.esm.repository.CertificateRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;

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

    public List<Certificate> findAll(List<String> tagNames, String name, String orderBy, String order,
                                     int page, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> query = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = query.from(Certificate.class);
        query.select(root);

        prepareSearchQuery(query, root, tagNames, name);

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
    public Long countFoundCertificates(List<String> tagNames, String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Certificate> root = query.from(Certificate.class);

        prepareSearchQuery(query, root, tagNames, name);

        query.select(criteriaBuilder.countDistinct(root));

        return entityManager.createQuery(query).getSingleResult();
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
        return entityManager.createQuery(COUNT_QUERY, Long.class)
                .getSingleResult();
    }

    private <T> void prepareSearchQuery(AbstractQuery<T> query, Root<Certificate> root, List<String> tagNames, String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotBlank(name)) {
            Predicate predicateForName = criteriaBuilder.like(root.get("name"), "%" + name + "%");
            predicates.add(predicateForName);
        }
        if (!CollectionUtils.isEmpty(tagNames)) {
            Join<Object, Object> tagListJoin = root.join("tags", JoinType.LEFT);
            Expression<Long> countOfTagsInGroup = criteriaBuilder.count(root);
            Predicate predicateTagsList = tagListJoin.get("name").in(tagNames);
            predicates.add(predicateTagsList);
            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                    .groupBy(root)
                    .having(criteriaBuilder.equal(countOfTagsInGroup, tagNames.size()));
        } else {
            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        }
    }

}
