package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.specification.impl.FindAllSpecification;
import com.epam.esm.specification.impl.FindByIdSpecification;
import com.epam.esm.specification.impl.user.UserByOrderIdSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.exception.CustomErrorCode.USER_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.SUCH_USER_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.USER_WITH_ID_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Translator translator;
    private final OrderServiceImpl orderService;

    private static final String USER_TABLE = "user";


    @Override
    public User add(User user) {
        return userRepository.add(user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.queryForList(new FindAllSpecification<>(USER_TABLE));
        users.forEach(this::addOrdersToUser);
        return users;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.queryForOne(new FindByIdSpecification<>(USER_TABLE, id))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(USER_WITH_ID_NOT_FOUND), id),
                        USER_NOT_FOUND.getErrorCode()));
        addOrdersToUser(user);
        return user;
    }

    @Override
    public User findByOrderId(Long orderId) {
        User user = userRepository.queryForOne(new UserByOrderIdSpecification(orderId))
                .orElseThrow(() -> new NoSuchEntityException(translator.toLocale(SUCH_USER_FOUND),
                        USER_NOT_FOUND.getErrorCode()));
        addOrdersToUser(user);
        return user;
    }

    @Override
    public boolean delete(Long id) {
        User user = findById(id);
        if (user == null) {
            throw new NoSuchEntityException(String.format(translator.toLocale(USER_WITH_ID_NOT_FOUND), id),
                    USER_NOT_FOUND.getErrorCode());
        }
        return userRepository.remove(user);
    }

    private void addOrdersToUser(User user) {
        List<Order> orders = orderService.findByUserId(user.getId());
        user.setOrders(orders);
    }

}
