package com.epam.esm.service.impl;

import com.epam.esm.configuration.Translator;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.CustomErrorCode.PAGE_INCORRECT_CODE;
import static com.epam.esm.exception.CustomErrorCode.USER_DUPLICATE_CODE;
import static com.epam.esm.exception.CustomErrorCode.USER_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.PAGE_INCORRECT;
import static com.epam.esm.exception.ErrorMessageCodeConstant.USER_ALREADY_EXISTS;
import static com.epam.esm.exception.ErrorMessageCodeConstant.USER_WITH_ID_NOT_FOUND;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepository;
    private final Translator translator;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public User add(User user) {
        Optional<User> optionalUser = findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new DuplicateException(translator.toLocale(USER_ALREADY_EXISTS), USER_DUPLICATE_CODE.getErrorCode());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(Role.ROLE_USER);
        return userRepository.add(user);
    }

    @Override
    public List<User> findAll(int limit, int page) {
        if (page <= 0 || limit <= 0) {
            throw new IncorrectDataException(translator.toLocale(PAGE_INCORRECT), PAGE_INCORRECT_CODE.getErrorCode());
        }
        List<User> users = userRepository.findAll(page, limit);
//        users.forEach(user -> user.setOrders(orderRepository.findByUser(user, 1, 10)));
        return users;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(USER_WITH_ID_NOT_FOUND), id),
                        USER_NOT_FOUND.getErrorCode()));
    }

    @Override
    @Transactional
    public User update(Long id, User user) {
        User fromDB = findById(id);
        fromDB.setName(ofNullable(user.getName()).orElse(fromDB.getName()));
        fromDB.setSurname(ofNullable(user.getSurname()).orElse(fromDB.getSurname()));
        fromDB.setEmail(ofNullable(user.getEmail()).orElse(fromDB.getEmail()));
        fromDB.setPassword(fromDB.getPassword());
        userRepository.update(fromDB);

        return fromDB;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = findById(id);
        userRepository.remove(user);
    }

    @Override
    public Long count() {
        return userRepository.count();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
