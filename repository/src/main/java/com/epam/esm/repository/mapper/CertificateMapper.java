package com.epam.esm.repository.mapper;

import com.epam.esm.entity.Certificate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CertificateMapper implements EntityMapper<Certificate> {

    private static final String CERTIFICATE_ID = "id";
    private static final String CERTIFICATE_NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";

    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Certificate certificate = new Certificate();
        certificate.setId(rs.getLong(CERTIFICATE_ID));
        certificate.setName(rs.getString(CERTIFICATE_NAME));
        certificate.setDescription(rs.getString(DESCRIPTION));
        certificate.setPrice(rs.getBigDecimal(PRICE));
        certificate.setDuration(rs.getInt(DURATION));
        certificate.setCreateDate(rs.getTimestamp(CREATE_DATE).toLocalDateTime());
        certificate.setLastUpdateDate(rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime());
        return certificate;
    }

}
