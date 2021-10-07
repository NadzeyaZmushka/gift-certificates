package com.epam.esm.validator;

import com.epam.esm.exception.CustomErrorCode;
import com.epam.esm.exception.IncorrectDataException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class TagValidator {

    public void validName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IncorrectDataException("Incorrect name ", CustomErrorCode.TAG_INCORRECT_DATA.getErrorCode());
        }
    }

}
