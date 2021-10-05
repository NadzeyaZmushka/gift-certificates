package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionInfoDTO {

    private String errorMessage;
    private Integer errorCode;

}
