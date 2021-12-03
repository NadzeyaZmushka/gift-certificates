package com.epam.esm.validator;

import com.epam.esm.configuration.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.IncorrectDataException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.epam.esm.exception.CustomErrorCode.CERTIFICATE_INCORRECT_DATA;
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_INCORRECT_DESCRIPTION;
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_INCORRECT_DURATION;
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_INCORRECT_NAME;
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_INCORRECT_NAME_LENGTH;
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_INCORRECT_PRICE;
import static com.epam.esm.exception.ErrorMessageCodeConstant.CERTIFICATE_INCORRECT_PRICE_MORE;

@Component
@RequiredArgsConstructor
public class CertificateValidator {

    private final Translator translator;

    public void validCertificate(Certificate certificate) {
        if (StringUtils.isBlank(certificate.getName())) {
            throw new IncorrectDataException(translator.toLocale(CERTIFICATE_INCORRECT_NAME),
                    CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }
        if (certificate.getName().length() > 50) {
            throw new IncorrectDataException(translator.toLocale(CERTIFICATE_INCORRECT_NAME_LENGTH),
                    CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }
        if (StringUtils.isBlank(certificate.getDescription())) {
            throw new IncorrectDataException(translator.toLocale(CERTIFICATE_INCORRECT_DESCRIPTION),
                    CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }
        if (certificate.getDuration() == null || certificate.getDuration() <= 0) {
            throw new IncorrectDataException(translator.toLocale(CERTIFICATE_INCORRECT_DURATION),
                    CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }
        if (certificate.getPrice().doubleValue() <= 0) {
            throw new IncorrectDataException(translator.toLocale(CERTIFICATE_INCORRECT_PRICE),
                    CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }
        if (certificate.getPrice().doubleValue() > 999) {
            throw new IncorrectDataException(translator.toLocale(CERTIFICATE_INCORRECT_PRICE_MORE),
                    CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }
    }

}
