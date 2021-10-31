package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.OrderRepository;
import com.epam.esm.repository.impl.UserRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.specification.impl.FindAllSpecification;
import com.epam.esm.specification.impl.FindByIdSpecification;
import com.epam.esm.specification.impl.order.OrderFindByUserIdSpecification;
import com.epam.esm.specification.impl.user.UserByOrderIdSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.exception.CustomErrorCode.ORDER_NOT_FOUND;
import static com.epam.esm.exception.CustomErrorCode.USER_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.ORDER_WITH_ID_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.USER_WITH_ID_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final Translator translator;
    private final CertificateService certificateService;

//    private final UserServiceImpl userService;
    private static final String ORDER_TABLE = "order";

    @Override
    public Order add(Order order) {
       return orderRepository.add(order);
    }

    @Override
    public Order create(Long userId, String certificateName) {
        Certificate certificate = certificateService.findByName(certificateName);
        BigDecimal cost = certificate.getPrice();
        User user = userRepository.queryForOne(new FindByIdSpecification<>("user", userId))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(USER_WITH_ID_NOT_FOUND), userId),
                USER_NOT_FOUND.getErrorCode()));
        Order order = new Order(cost, LocalDateTime.now(), user, certificate);

        return orderRepository.add(order);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = orderRepository.queryForList(new FindAllSpecification<>(ORDER_TABLE));
        orders.forEach(this::addCertificateToOrder);
//        orders.forEach(this::addUserToOrder);
        return orders;
    }

    @Override
    public Order findById(Long id) {
        Order order = orderRepository.queryForOne(new FindByIdSpecification<>(ORDER_TABLE, id))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(ORDER_WITH_ID_NOT_FOUND), id), ORDER_NOT_FOUND.getErrorCode()));
        addCertificateToOrder(order);
//        addUserToOrder(order);
        return order;
    }

    @Override
    public boolean delete(Long id) {
        Order order = findById(id);
        return orderRepository.remove(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        List<Order> orders = orderRepository.queryForList(new OrderFindByUserIdSpecification(userId));
        orders.forEach(this::addCertificateToOrder);
//        orders.forEach(this::addUserToOrder);
        return orders;
    }

    private void addCertificateToOrder(Order order) {
        Certificate certificate = certificateService.findByOrderId(order.getId());
        order.setCertificate(certificate);
    }

//    private void addUserToOrder(Order order) {
//        User user = userRepository.queryForOne(new UserByOrderIdSpecification(order.getId()))
//                .orElseThrow(() -> new NoSuchEntityException(translator.toLocale(USER_WITH_ID_NOT_FOUND),
//                        USER_NOT_FOUND.getErrorCode()));
//        order.setUser(user);
//
//    }

}
