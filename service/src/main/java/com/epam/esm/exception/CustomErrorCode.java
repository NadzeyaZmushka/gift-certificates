package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    CERTIFICATE_NOT_FOUND(40401),
    CERTIFICATE_INCORRECT_DATA(40001),
    TAG_NOT_FOUND(40402),
    TAG_INCORRECT_DATA(40002),
    USER_NOT_FOUND(40403),
    ORDER_NOT_FOUND(40404);

    private final int errorCode;

}
