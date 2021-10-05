package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.mapper.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class CertificateRepositoryImpl extends BaseCrudRepository<Certificate> implements CertificateRepository {

    private static final String ADD_CERTIFICATE_SQL = "INSERT INTO gift_certificates.certificate (name, description, price" +
            ", duration, create_date, last_update_date) " +
            "VALUES (:name, :description, :price, :duration, :createDate, :lastUpdateDate)";
    private static final String DELETE_CERTIFICATE_SQL = "DELETE FROM gift_certificates.certificate " +
            "WHERE id = :id";
    private static final String UPDATE_CERTIFICATE_SQL = "UPDATE gift_certificates.certificate SET name = :name" +
            ", description = :description, price = :price, duration = :duration" +
            ", create_date = :createDate, last_update_date = :lastUpdateDate";

    @Autowired
    public CertificateRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, EntityMapper<Certificate> mapper) {
        super(jdbcTemplate, mapper);
    }

    @Override
    public void addTagToCertificate(Long certificatedId, Tag tag) {

    }

    @Override
    protected String getSqlQueryCreate() {
        return ADD_CERTIFICATE_SQL;
    }

    @Override
    protected String getSqlQueryUpdate(Certificate entity) {
        return UPDATE_CERTIFICATE_SQL;
    }

    @Override
    protected String getSqlQueryDelete() {
        return DELETE_CERTIFICATE_SQL;
    }

    @Override
    protected SqlParameterSource getSqlParameterSource(Certificate certificate) {
        return new MapSqlParameterSource()
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration())
                .addValue("createDate", Timestamp.valueOf(certificate.getCreateDate()))
                .addValue("lastUpdateDate", Timestamp.valueOf(certificate.getLastUpdateDate()));

    }

    @Override
    protected SqlParameterSource getSqlParameterSourceForUpdate(Certificate certificate) {
        return new MapSqlParameterSource()
                .addValue("id", certificate.getId());
    }
}
