package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.BaseCrudRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.specification.impl.FindAllSpecification;
import com.epam.esm.specification.impl.FindByIdSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.exception.CustomErrorCode.USER_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.USER_WITH_ID_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BaseCrudRepository<User> userRepository;
    private final Translator translator;
    private static final String USER_TABLE = "user";


    @Override
    public User add(User entity) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.queryForList(new FindAllSpecification<>(USER_TABLE));
    }

    @Override
    public User findById(Long id) {
        return userRepository.queryForOne(new FindByIdSpecification<>(USER_TABLE, id))
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(USER_WITH_ID_NOT_FOUND), id),
                        USER_NOT_FOUND.getErrorCode()));
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

}
