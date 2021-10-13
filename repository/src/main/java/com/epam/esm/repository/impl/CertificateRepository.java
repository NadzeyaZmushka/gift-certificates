package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.EntityMapper;
import com.epam.esm.repository.BaseCrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class CertificateRepository extends BaseCrudRepository<Certificate> {

    private static final String ADD_CERTIFICATE_SQL = "INSERT INTO gifts.certificate (name, description, price" +
            ", duration, create_date, last_update_date) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CERTIFICATE_SQL = "UPDATE gifts.certificate SET name = ?" +
            ", description = ?, price = ?, duration = ?" +
            ", create_date = ?, last_update_date = ? WHERE id = ?";

    public CertificateRepository(JdbcTemplate jdbcTemplate, EntityMapper<Certificate> mapper) {
        super(jdbcTemplate, mapper);
    }

    @Override
    protected PreparedStatement prepareAddStatement(Connection connection, Certificate certificate) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(ADD_CERTIFICATE_SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, certificate.getName());
        ps.setString(2, certificate.getDescription());
        ps.setBigDecimal(3, certificate.getPrice());
        ps.setInt(4, certificate.getDuration());
        ps.setObject(5, certificate.getCreateDate());
        ps.setObject(6, certificate.getLastUpdateDate());
        return ps;
    }

    @Override
    protected String getUpdateSql() {
       return UPDATE_CERTIFICATE_SQL;
    }

    @Override
    protected Object[] getParam(Certificate entity) {
        return new Object[]{entity.getName(),
        entity.getDescription(),
        entity.getPrice(),
        entity.getDuration(),
        entity.getCreateDate(),
        entity.getLastUpdateDate(),
        entity.getId()};
    }

    @Override
    protected String getTableName() {
        return "certificate";
    }
    @Override
    public void addAll(List<Certificate> tagCertificateList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAll(List<Certificate> tagCertificateList) {
        throw new UnsupportedOperationException();
    }

}
