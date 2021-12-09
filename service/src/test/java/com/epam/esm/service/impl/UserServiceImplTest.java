package com.epam.esm.service.impl;

import com.epam.esm.configuration.Translator;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testShouldReturnCountOfUsers() {
        when(userRepository.count()).thenReturn(10L);
        Long actual = userService.count();
        assertEquals(10, actual);
    }

}
