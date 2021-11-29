package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.exception.CustomErrorCode.PAGE_INCORRECT_CODE;
import static com.epam.esm.exception.CustomErrorCode.USER_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.PAGE_INCORRECT;
import static com.epam.esm.exception.ErrorMessageCodeConstant.USER_WITH_ID_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepository;
    private final Translator translator;

    @Override
    @Transactional
    public User add(User user) {
        return userRepository.add(user);
    }

    @Override
    public List<User> findAll(int limit, int page) {
        if (page <= 0 || limit <= 0) {
            throw new IncorrectDataException(translator.toLocale(PAGE_INCORRECT), PAGE_INCORRECT_CODE.getErrorCode());
        }
        return userRepository.findAll(page, limit);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(USER_WITH_ID_NOT_FOUND), id),
                        USER_NOT_FOUND.getErrorCode()));
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

}
