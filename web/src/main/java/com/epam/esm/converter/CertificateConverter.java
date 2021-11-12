package com.epam.esm.converter;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CertificateConverter {

    private final ModelMapper modelMapper;

    public Certificate toEntity(CertificateDTO certificateDTO) {
        return Objects.isNull(certificateDTO) ? null : modelMapper.map(certificateDTO, Certificate.class);
    }

    public CertificateDTO toDTO(Certificate certificate) {
        return Objects.isNull(certificate) ? null : modelMapper.map(certificate, CertificateDTO.class);
    }

}
