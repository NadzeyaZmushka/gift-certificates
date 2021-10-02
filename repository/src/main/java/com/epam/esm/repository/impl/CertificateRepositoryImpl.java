package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.repository.EntityRepository;
import com.epam.esm.repository.specification.SqlSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificateRepositoryImpl implements EntityRepository<Certificate, SqlSpecification> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Certificate> queryForList(SqlSpecification specification) {
        return null;
    }

    @Override
    public Certificate queryForOne(SqlSpecification specification) {
        return null;
    }

    @Override
    public Certificate add(Certificate entity) {
        return null;
    }

    @Override
    public boolean update(Certificate entity) {
        return false;
    }

    @Override
    public boolean remove(Certificate entity) {
        return false;
    }

}
