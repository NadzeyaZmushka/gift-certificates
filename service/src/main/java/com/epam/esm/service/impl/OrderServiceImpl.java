package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.BaseCrudRepository;
import com.epam.esm.repository.CrudRepository;
import com.epam.esm.repository.impl.OrderRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.specification.impl.FindAllSpecification;
import com.epam.esm.specification.impl.FindByIdSpecification;
import com.epam.esm.specification.impl.order.OrderFindByUserIdSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.exception.CustomErrorCode.ORDER_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.ORDER_WITH_ID_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final Translator translator;
    private final CertificateService certificateService;

    private static final String ORDER_TABLE = "order";

    //???
    @Override
    public Order add(Order order) {
        order.setCreateDate(LocalDateTime.now());
        Order newOrder = orderRepository.add(order);
//        Certificate certificate = certificateService.findByOrderId(newOrder.getId());
//        newOrder.setCost(certificate.getPrice());
//        newOrder.setCertificate(certificate);
        return newOrder;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders;
        orders = orderRepository.queryForList(new FindAllSpecification<>(ORDER_TABLE));

        return orders;
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.queryForOne(new FindByIdSpecification<>(ORDER_TABLE, id))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(ORDER_WITH_ID_NOT_FOUND), id), ORDER_NOT_FOUND.getErrorCode()));
    }

    @Override
    public boolean delete(Long id) {
        Order order = findById(id);
        return orderRepository.remove(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.queryForList(new OrderFindByUserIdSpecification(userId));
    }

}
