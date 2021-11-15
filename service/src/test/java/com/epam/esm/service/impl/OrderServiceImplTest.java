package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
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

//    @Test
//    void testShouldReturnAllOrders() {
//        //given
//        List<Tag> tags = Collections.singletonList(new Tag(1L, "tag"));
//        Certificate certificate = new Certificate(1L, "name", "description", new BigDecimal(100), 10,
//                LocalDateTime.now(), LocalDateTime.now(), tags);
//        User user = new User(1L, "name", "surname", null);
//        List<Order> orders = Collections.singletonList(new Order(1L, new BigDecimal(100), LocalDateTime.now(), user, certificate));
//        user.setOrders(orders);
//        when(orderRepository.findAll(1, 10)).thenReturn(orders);
//        //when
//        List<Order> actual = orderService.findAll(10, 1);
//        //then
//        assertEquals(orders.size(), actual.size());
//        assertEquals(orders, actual);
//    }


}
