package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CertificateRepositoryImpl implements CertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findCertificateTags(Long certificateId) {
        return null;
    }

    @Override
    public List<Certificate> findAll() {
        return null;
    }

    @Override
    public Certificate findById(Long id) {
        return null;
    }

    @Override
    public Certificate create(Certificate entity) {
        return null;
    }

    @Override
    public boolean update(Certificate entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
