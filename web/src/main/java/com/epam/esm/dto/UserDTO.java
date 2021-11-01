package com.epam.esm.dto;

import com.epam.esm.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseEntityDTO<UserDTO> {

    private String name;
    private String surname;
    private List<OrderDTO> orders;

}
