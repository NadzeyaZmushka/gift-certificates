package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.OrderRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.epam.esm.exception.CustomErrorCode.ORDER_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.ORDER_WITH_ID_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserServiceImpl userService;
    private final Translator translator;
    private final CertificateService certificateService;

    @Override
    @Transactional
    public Order add(Order order) {
        return orderRepository.add(order);
    }

    @Override
    @Transactional
    public Order create(Long userId, String certificateName) {
        User user = userService.findById(userId);
        Certificate certificate = certificateService.findByName(certificateName);
        BigDecimal cost = certificate.getPrice();
        Order order = Order.builder()
                .cost(cost)
                .createDate(LocalDateTime.now())
                .user(user)
                .certificate(certificate).build();
        orderRepository.add(order);
        return order;
    }

    @Override
    @Transactional
    public List<Order> findAll(int limit, int page) {
        return orderRepository.findAll(page, limit);
    }

    @Override
    @Transactional
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(ORDER_WITH_ID_NOT_FOUND), id),
                        ORDER_NOT_FOUND.getErrorCode()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = findById(id);
        orderRepository.remove(order);
    }

    @Override
    @Transactional
    public List<Order> findByUserId(Long userId, int page, int pageSize) {
        User user = userService.findById(userId);
        return orderRepository.findByUserId(user, page, pageSize);
    }

}
