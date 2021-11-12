package com.epam.esm.converter;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final ModelMapper modelMapper;

    public User toEntity(UserDTO userDTO) {
        return Objects.isNull(userDTO) ? null : modelMapper.map(userDTO, User.class);
    }

    public UserDTO toDTO(User user) {
        return Objects.isNull(user) ? null : modelMapper.map(user, UserDTO.class);
    }

}
