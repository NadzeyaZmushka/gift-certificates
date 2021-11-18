package com.epam.esm.validator;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CustomErrorCode;
import com.epam.esm.exception.ErrorMessageCodeConstant;
import com.epam.esm.exception.IncorrectDataException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TagValidator {

    private final Translator translator;

    public void validTag(Tag tag) {
        if (StringUtils.isBlank(tag.getName()) || StringUtils.isNumeric(tag.getName())) {
            throw new IncorrectDataException(translator.toLocale(ErrorMessageCodeConstant.TAG_INCORRECT_NAME),
                    CustomErrorCode.TAG_INCORRECT_DATA.getErrorCode());
        }
        if (tag.getName().length() > 50) {
            throw new IncorrectDataException(translator.toLocale(ErrorMessageCodeConstant.TAG_INCORRECT_NAME_LENGTH),
                    CustomErrorCode.TAG_INCORRECT_DATA.getErrorCode());
        }
    }

}
