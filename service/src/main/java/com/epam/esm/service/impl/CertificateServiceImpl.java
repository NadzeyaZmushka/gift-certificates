package com.epam.esm.service.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CustomErrorCode;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.specification.impl.FindAllSpecification;
import com.epam.esm.repository.specification.impl.FindByIdSpecification;
import com.epam.esm.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepositoryImpl certificateRepository;

    @Override
    public Certificate add(Certificate certificate) {
        certificateRepository.add(certificate);
        return certificate;
    }

    @Override
    public List<Certificate> findAll() {
        return certificateRepository.queryForList(new FindAllSpecification("certificate"));
    }

    @Override
    public Certificate findById(long id) {
        return certificateRepository.queryForOne(new FindByIdSpecification("certificate", id))
                .orElseThrow(() -> new NoSuchEntityException("No certificate with id = " + id, CustomErrorCode.CERTIFICATE_NOT_FOUND.getErrorCode()));
    }

    @Override
    public void delete(Long id) {
        Certificate certificate = findById(id);
        certificateRepository.remove(certificate);
    }

    @Override
    public Certificate update(Certificate certificate) {
        return certificateRepository.update(certificate);
    }

    //???
    @Override
    public void addTagToCertificate(Long certificateId, Tag tag) {
        certificateRepository.addTagToCertificate(certificateId, tag);
    }

    @Override
    public void deleteTagFromCertificate(Long certificateId, Tag tag) {
        certificateRepository.deleteTagFromCertificate(certificateId, tag);
    }

}
