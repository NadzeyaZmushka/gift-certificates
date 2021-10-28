package com.epam.esm.repository.impl;

import com.epam.esm.entity.CertificateAndOrder;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.QueryOptions;
import com.epam.esm.specification.SqlSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CertificateToOrderRepository implements CrudRepository<CertificateAndOrder> {

    private static final String CREATE_ORDER_CERTIFICATE_SQL = "INSERT INTO gifts.order_certificate (order_id, certificate_id) " +
            "VALUES (?, ?)";
    private static final String DELETE_ORDER_CERTIFICATE_SQL = "DELETE FROM gifts.order_certificate " +
            "WHERE order_id = ? AND certificate_id = ?";

    private static final String NOT_SUPPORTED = "This operation is not supported";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public CertificateAndOrder add(CertificateAndOrder entity) {
        jdbcTemplate.update(CREATE_ORDER_CERTIFICATE_SQL, entity.getOrderId(), entity.getCertificateId());
        return entity;
    }

    @Override
    public boolean remove(CertificateAndOrder entity) {
        return jdbcTemplate.update(DELETE_ORDER_CERTIFICATE_SQL, entity.getOrderId(), entity.getCertificateId()) != 0;
    }

    @Override
    public void addAll(List<CertificateAndOrder> certificateAndOrderList) {
        jdbcTemplate.batchUpdate(CREATE_ORDER_CERTIFICATE_SQL, certificateAndOrderList.stream()
                .map(oc -> new Object[]{oc.getOrderId(), oc.getCertificateId()})
                .collect(Collectors.toList()));
    }

    @Override
    public void removeAll(List<CertificateAndOrder> certificateAndOrderList) {
        jdbcTemplate.batchUpdate(DELETE_ORDER_CERTIFICATE_SQL, certificateAndOrderList.stream()
                .map(oc -> new Object[]{oc.getOrderId(), oc.getCertificateId()})
                .collect(Collectors.toList()));
    }

    @Override
    public List<CertificateAndOrder> queryForList(SqlSpecification<CertificateAndOrder> specification) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public List<CertificateAndOrder> queryForList(SqlSpecification<CertificateAndOrder> specification, QueryOptions options) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public Optional<CertificateAndOrder> queryForOne(SqlSpecification<CertificateAndOrder> specification) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public CertificateAndOrder update(CertificateAndOrder entity) {
        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

}