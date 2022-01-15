package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String FIND_ORDER_BY_USER_ID_QUERY = "select o from Order o where o.user =: user";
    private static final String FIND_ALL_QUERY = "select o from Order o";
    private static final String COUNT_QUERY = "select count(o) from Order o";

    @Override
    public List<Order> findAll(int page, int pageSize) {
        return entityManager.createQuery(FIND_ALL_QUERY, Order.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }


    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    public List<Order> findByUser(User user, int page, int pageSize) {
        return entityManager.createQuery(FIND_ORDER_BY_USER_ID_QUERY, Order.class)
                .setParameter("user", user)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public Order add(Order entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void update(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Order entity) {
        entityManager.remove(entity);
    }

    @Override
    public Long count() {
        return entityManager.createQuery(COUNT_QUERY, Long.class)
                .getSingleResult();
    }

    public Long countFoundOrders(Long userId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Order> root = query.from(Order.class);
        Predicate predicate = null;
        if (userId != null) {
            predicate = criteriaBuilder.equal(root.get("user"), userId);
        }
        query.where(predicate);
        query.select(criteriaBuilder.countDistinct(root));
        return entityManager.createQuery(query).getSingleResult();
    }

}
