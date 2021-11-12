package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager entityManager;

    private static final String FIND_ORDER_BY_USER_ID_QUERY = "select o from Order o where o.user =: user";
    private static final String FIND_ALL_QUERY = "select o from Order o";
    private static final String COUNT_QUERY = "select o from Order o";

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

    public List<Order> findByUserId(User user, int page, int pageSize) {
        return entityManager.createQuery(FIND_ORDER_BY_USER_ID_QUERY, Order.class)
                .setParameter("user", user)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public Order add(Order entity) {
        entity.setCreateDate(LocalDateTime.now());
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
        return (Long) entityManager.createQuery(COUNT_QUERY)
                .getSingleResult();
    }

}
