package com.epam.esm.validator;

import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.CustomErrorCode;
import com.epam.esm.exception.IncorrectDataException;
import org.apache.commons.lang3.StringUtils;

public class CertificateValidator {

    //todo дописать регулярку
    public boolean validName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IncorrectDataException("Incorrect name ", CustomErrorCode.CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }
        return true;
    }

}
