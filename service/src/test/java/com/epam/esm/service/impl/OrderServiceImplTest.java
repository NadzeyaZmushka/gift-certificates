package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.OrderRepositoryImpl;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    @Mock
    private OrderRepositoryImpl orderRepository;
    @Mock
    private CertificateRepositoryImpl certificateRepository;
    @Mock
    private UserRepositoryImpl userRepository;
    @Mock
    private Translator translator;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testShouldReturnAllOrders() {
        //given
        Certificate certificate = new Certificate(1L, "name", "description", new BigDecimal(100),
                10, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
        User user = new User(1L, "name", "surname", new ArrayList<>());
        List<Order> orders = List.of(new Order(1L, certificate.getPrice(), LocalDateTime.now(), user, certificate));
        when(orderRepository.findAll(1, 10)).thenReturn(orders);
        //when
        List<Order> actual = orderService.findAll(10, 1);
        //then
        assertEquals(orders.size(), actual.size());
        assertEquals(orders, actual);
    }

    //todo
    @Test
    void testShouldCreateNewOrder() {
        //given
        Certificate certificate = new Certificate(1L, "name", "description", new BigDecimal(100),
                10, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
        User user = new User(1L, "name", "surname", new ArrayList<>());
        Order order = new Order(1L, certificate.getPrice(), LocalDateTime.now(), user, certificate);
        when(orderRepository.add(order)).thenReturn(order);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(certificateRepository.findByName("name")).thenReturn(Optional.of(certificate));
        //when
        Order actual = orderService.add(order);
        //then
        assertEquals(order.getId(), actual.getId());

    }
}
