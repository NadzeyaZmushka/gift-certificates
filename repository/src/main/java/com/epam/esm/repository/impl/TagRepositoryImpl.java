package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String FIND_ALL_QUERY = "select t from Tag t";
    private static final String FIND_BY_NAME_QUERY = "select t from Tag t where t.name =: name";
    private static final String FIND_BY_CERTIFICATE_ID_QUERY = "select c from Certificate c where c.id = :id";
    private static final String COUNT_QUERY = "select count(t) from Tag t";
    private static final String MOST_POPULAR_TAG_SQL = "SELECT gifts.tag.id, gifts.tag.name " +
            "FROM gifts.tag INNER JOIN gifts.certificate_tag ct on tag.id = ct.tag_id " +
            "INNER JOIN gifts.certificate c on c.id = ct.certificate_id " +
            "INNER JOIN gifts.order o on c.id = o.certificate_id " +
            "INNER JOIN gifts.user u on u.id = o.user_id " +
            "WHERE u.id IN (SELECT wu.user_id " +
            "FROM (SELECT SUM(gifts.order.cost) sumCost, user_id " +
            "FROM gifts.order GROUP BY user_id ORDER BY sumCost desc limit 1) as wu) " +
            "GROUP BY tag.id " +
            "ORDER BY count(tag.id) desc limit 1";

    @Override
    public List<Tag> findAll(int page, int pageSize) {
        return entityManager.createQuery(FIND_ALL_QUERY, Tag.class)
                .setFirstResult(pageSize * (page - 1))
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Tag add(Tag entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void update(Tag entity) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void remove(Tag entity) {
        entityManager.remove(entity);
    }

    public Optional<Tag> findByName(String name) {
        try {
            return Optional.of(entityManager.createQuery(FIND_BY_NAME_QUERY, Tag.class)
                    .setParameter("name", name)
                    .setMaxResults(1)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Tag> findByNames(List<String> tagNames) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        root.in(tagNames);
        query.select(root);
        return entityManager.createQuery(query)
                .getResultList();
    }

    public List<Tag> findByCertificateId(Long id) {
        Certificate certificate = entityManager.createQuery(FIND_BY_CERTIFICATE_ID_QUERY, Certificate.class)
                .setParameter("id", id)
                .getSingleResult();
        return certificate.getTags();
    }

    public Optional<Tag> findMostPopularTag() {
        Query query = entityManager.createNativeQuery(MOST_POPULAR_TAG_SQL, Tag.class);
        try {
            Tag tag = (Tag) query.getSingleResult();
            return Optional.ofNullable(tag);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long count() {
        return entityManager.createQuery(COUNT_QUERY, Long.class)
                .getSingleResult();
    }

}
