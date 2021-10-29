package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.EntityMapper;
import com.epam.esm.repository.BaseCrudRepository;
import com.epam.esm.specification.SqlSpecification;
import com.epam.esm.specification.impl.certificate.CertificateByOrderIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository extends BaseCrudRepository<Order> {

    private static final String ADD_ORDER_SQL = "INSERT INTO gifts.order (cost, user_id, create_date, certificate_id) " +
            "VALUES (?, ?, ?, ?)";

    @Autowired
    private CertificateRepository certificateRepository;

    public OrderRepository(JdbcTemplate jdbcTemplate, EntityMapper<Order> mapper) {
        super(jdbcTemplate, mapper);
    }

    @Override
    protected String getTableName() {
        return "order";
    }

    @Override
    public List<Order> queryForList(SqlSpecification<Order> specification) {
        List<Order> orders = super.queryForList(specification);
        orders.forEach(this::addCertificateToOrder);
        return orders;
    }

    @Override
    public Optional<Order> queryForOne(SqlSpecification<Order> specification) {
        Optional<Order> order = super.queryForOne(specification);
        order.ifPresent(this::addCertificateToOrder);
        return order;
    }

    @Override
    public Order add(Order entity) {
        super.add(entity);
        Certificate certificate = entity.getCertificate();
        Certificate savedCertificate = certificateRepository.add(certificate);
        entity.setCertificate(savedCertificate);
        return entity;
    }

    //???
    @Override
    protected PreparedStatement prepareAddStatement(Connection connection, Order order) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(ADD_ORDER_SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setBigDecimal(1, order.getCost());
        ps.setLong(2, order.getUser().getId());
        ps.setObject(3, order.getCreateDate());
        ps.setLong(4, order.getCertificate().getId());
        return ps;
    }

    @Override
    protected String getUpdateSql() {
        return null;
    }

    @Override
    protected Object[] getParam(Order entity) {
        return new Object[]{entity.getCost(),
                entity.getUser().getId(),
                entity.getCreateDate()};
    }

    @Override
    public void addAll(List<Order> tagCertificateList) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAll(List<Order> tagCertificateList) {
        throw new UnsupportedOperationException();
    }

    private void addCertificateToOrder(Order order) {
        Optional<Certificate> certificate = certificateRepository.queryForOne(new CertificateByOrderIdSpecification(order.getId()));
        order.setCertificate(certificate.orElseThrow());
    }

}
