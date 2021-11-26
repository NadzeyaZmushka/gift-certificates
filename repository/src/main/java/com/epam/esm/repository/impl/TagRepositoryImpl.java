package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_QUERY = "select t from Tag t";
    private static final String FIND_BY_NAME_QUERY = "select t from Tag t where t.name =: name";
    private static final String FIND_BY_CERTIFICATE_ID_QUERY = "select c from Certificate c where c.id = :id";
    private static final String COUNT_QUERY = "select count(t) from Tag t";
    private static final String MOST_POPULAR_TAG_SQL = "SELECT gifts.tag.id, gifts.tag.name, count(*) count, u.id as u_id\n" +
            "FROM gifts.tag\n" +
            "         INNER JOIN gifts.certificate_tag ct on tag.id = ct.tag_id\n" +
            "         INNER JOIN gifts.certificate c on c.id = ct.certificate_id\n" +
            "         INNER JOIN gifts.order o on c.id = o.certificate_id\n" +
            "         INNER JOIN gifts.user u on u.id = o.user_id\n" +
            "WHERE u.id IN (SELECT wu.user_id\n" +
            "               FROM (SELECT SUM(gifts.order.cost) sumCost, user_id\n" +
            "                     FROM gifts.order\n" +
            "                     GROUP BY user_id\n" +
            "                    ) as wu\n" +
            "               WHERE sumCost IN (SELECT max(sCui.sumCost)\n" +
            "                                 FROM (SELECT SUM(gifts.order.cost) sumCost\n" +
            "                                       FROM gifts.order\n" +
            "                                       GROUP BY user_id) as sCui\n" +
            "               ))\n" +
            "GROUP BY tag.id, u_id\n" +
            "ORDER BY count desc";

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


    public List<Map<String, String>> findMostPopularTag() {
        List<Object[]> results = entityManager.createNativeQuery(MOST_POPULAR_TAG_SQL).getResultList();
        List<Map<String, String>> mapList = new ArrayList<>();
        for (Object[] result : results) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("tagId", result[0].toString());
            hashMap.put("tagName", (String) result[1]);
            hashMap.put("count", result[2].toString());
            hashMap.put("userId", result[3].toString());
            mapList.add(hashMap);
        }
        return mapList;

    }

    @Override
    public Long count() {
        return entityManager.createQuery(COUNT_QUERY, Long.class)
                .getSingleResult();
    }

}
