package com.epam.esm.dto;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderDTO extends BaseEntityDTO {

    private BigDecimal cost;
    private LocalDateTime createDate;
    private User user;
    private Certificate certificate;

}
