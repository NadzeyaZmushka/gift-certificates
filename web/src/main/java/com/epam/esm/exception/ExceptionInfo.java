package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionInfo {

    private List<String> errorMessage;
    private Integer errorCode;

}
