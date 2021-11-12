package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    private static final String FIND_ALL_QUERY = "select u from User u";
    private static final String COUNT_QUERY = "select count(u) from User u";

    @Override
    public List<User> findAll(int page, int pageSize) {
        return entityManager.createQuery(FIND_ALL_QUERY, User.class)
                .setFirstResult(pageSize * (page - 1))
                .setMaxResults(pageSize)
                .getResultList();
    }


    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public User add(User entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void update(User entity) {
        entityManager.merge(entity);
    }

    @Override
    public void remove(User entity) {
        entityManager.remove(entity);
    }

    @Override
    public Long count() {
        return (Long) entityManager.createQuery(COUNT_QUERY)
                .getSingleResult();
    }

}
