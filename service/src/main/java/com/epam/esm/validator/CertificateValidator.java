package com.epam.esm.validator;

import com.epam.esm.config.Translator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.IncorrectDataException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static com.epam.esm.exception.CustomErrorCode.CERTIFICATE_INCORRECT_DATA;

@Component
@RequiredArgsConstructor
public class CertificateValidator {

    private final Translator translator;

    public void validCertificate(Certificate certificate) {
        if (StringUtils.isBlank(certificate.getName()) && certificate.getName().length() > 50) {
            throw new IncorrectDataException(translator.toLocale("certificate.incorrectName"),
                    CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }
        if (StringUtils.isBlank(certificate.getDescription())) {
            throw new IncorrectDataException(translator.toLocale("certificate.incorrectDescription"),
                    CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }
        if (certificate.getDuration() == null || certificate.getDuration() <= 0) {
            throw new IncorrectDataException("certificate.incorrectDuration",
                    CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }
        if (certificate.getPrice() == null) {
            throw new IncorrectDataException("certificate.incorrectPrice",
                    CERTIFICATE_INCORRECT_DATA.getErrorCode());
        }

    }

}
