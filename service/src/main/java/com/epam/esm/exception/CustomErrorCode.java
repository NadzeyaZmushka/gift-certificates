package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    TAG_NOT_FOUND(40402),
    CERTIFICATE_NOT_FOUND(40401);

    private final int errorCode;

}
