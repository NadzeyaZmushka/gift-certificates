package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class CertificateRepositoryImpl extends BaseCrudRepository<Certificate> implements CertificateRepository {


    @Autowired
    public CertificateRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, EntityMapper<Certificate> mapper) {
        super(jdbcTemplate, mapper);
    }

    @Override
    public void addTagToCertificate(Long certificatedId, Tag tag) {

    }

    @Override
    protected String getSqlQueryCreate() {
        return null;
    }

    @Override
    protected String getSqlQueryUpdate(Certificate entity) {
        return null;
    }

    @Override
    protected String getSqlQueryDelete() {
        return null;
    }

    @Override
    protected SqlParameterSource getSqlParameterSource(Certificate entity) {
        return null;
    }

    @Override
    protected SqlParameterSource getSqlParameterSourceForUpdate(Certificate entity) {
        return null;
    }
}
