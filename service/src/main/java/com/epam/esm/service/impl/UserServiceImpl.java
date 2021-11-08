package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.exception.CustomErrorCode.USER_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.USER_WITH_ID_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Translator translator;

    @Override
    public User add(User user) {
        return userRepository.add(user);
    }

    @Override
    public List<User> findAll(int limit, int page) {
        return userRepository.findAll(page, limit);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(USER_WITH_ID_NOT_FOUND), id),
                        USER_NOT_FOUND.getErrorCode()));
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        if (user == null) {
            throw new NoSuchEntityException(String.format(translator.toLocale(USER_WITH_ID_NOT_FOUND), id),
                    USER_NOT_FOUND.getErrorCode());
        }
        userRepository.remove(user);
    }

}
