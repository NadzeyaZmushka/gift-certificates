package com.epam.esm.service.impl;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.User;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepositoryImpl userRepository;
    @Mock
    private Translator translator;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testShouldReturnUserWithSuchId() {
        //given
        User user = new User(1L, "name", "surname", new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        //when
        User actual = userService.findById(1L);
        //then
        assertEquals(user, actual);
    }

    @Test
    void testShouldReturnAllUsers() {
        //given
        List<User> userList = Arrays.asList(new User(1L, "name", "surname", new ArrayList<>()),
                new User(2L, "name2", "surname2", new ArrayList<>()));
        when(userRepository.findAll(1, 10)).thenReturn(userList);
        //when
        List<User> actual = userService.findAll(10, 1);
        //then
        assertEquals(userList.size(), actual.size());
        assertEquals(userList, actual);
    }

    @Test
    void testShouldCreateNewUser() {
        //given
        User user = new User(1L, "name", "surname", new ArrayList<>());
        User addUser = new User("name", "surname", new ArrayList<>());
        when(userRepository.add(addUser)).thenReturn(user);
        //when
        User actual = userService.add(addUser);
        //then
        assertEquals(user.getName(), actual.getName());
        assertEquals(1L, actual.getId());
        assertNotNull(actual.getId());
    }

}