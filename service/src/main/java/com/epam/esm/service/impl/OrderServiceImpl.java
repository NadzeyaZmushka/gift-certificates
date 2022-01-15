package com.epam.esm.service.impl;

import com.epam.esm.configuration.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.exception.CustomErrorCode.ORDER_NOT_FOUND;
import static com.epam.esm.exception.CustomErrorCode.PAGE_INCORRECT_CODE;
import static com.epam.esm.exception.ErrorMessageCodeConstant.ORDER_WITH_ID_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.PAGE_INCORRECT;

@Slf4j
@Service
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
    public Order create(Long userId, Long certificateId) {
        User user = userService.findById(userId);
        Certificate certificate = certificateService.findById(certificateId);
        Order newOrder = Order.builder()
                .user(user)
                .certificate(certificate).build();
        return orderRepository.add(newOrder);
    }

    @Override
    public List<Order> findAll(int limit, int page) {
        if (page <= 0 || limit <= 0) {
            throw new IncorrectDataException(translator.toLocale(PAGE_INCORRECT), 40400);
        }
        return orderRepository.findAll(page, limit);
    }

    @Override
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
    public Long count() {
        return orderRepository.count();
    }

    @Override
    public Long countFoundOrders(Long userId) {
        Long count;
        if (userId == null) {
            count = count();
        } else {
            count = orderRepository.countFoundOrders(userId);
        }
        return count;
    }

        @Override
        @Transactional
        public List<Order> findAllByUserId (Long userId,int page, int limit){
            if (page <= 0 || limit <= 0) {
                throw new IncorrectDataException(translator.toLocale(PAGE_INCORRECT), PAGE_INCORRECT_CODE.getErrorCode());
            }
            List<Order> orders;
            if (userId == null) {
                orders = findAll(limit, page);
            } else {
                User user = userService.findById(userId);
                orders = orderRepository.findByUser(user, page, limit);
            }
            return orders;
        }

    }
