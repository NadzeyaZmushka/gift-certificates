package com.epam.esm.impl;

import com.epam.esm.EntityService;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.specification.impl.FindAllSpecification;
import com.epam.esm.repository.specification.impl.FindByIdSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateService implements EntityService<Certificate> {

    CertificateRepositoryImpl certificateRepository;

    @Override
    public Certificate add(Certificate certificate) {
        certificateRepository.add(certificate);
        log.info("Certificate was added");
        return certificate;
    }

    @Override
    public List<Certificate> findAll() {
        return certificateRepository.queryForList(new FindAllSpecification("certificate"));
    }

    @Override
    public Certificate findById(long id) {
        return certificateRepository.queryForOne(new FindByIdSpecification("certificate", id))
                .orElseThrow(() -> new NoSuchEntityException("No certificate with id = " + id));
    }

    @Override
    public boolean delete(Long id) {
        Certificate certificate = findById(id);
        if (certificate == null) {
            log.error("There is no certificate with id = " + id);
            throw new NoSuchEntityException("There is no certificate with id = " + id);
        }
        return certificateRepository.remove(certificate);
    }

    @Override
    public boolean update(Certificate certificate) {
        return certificateRepository.update(certificate);
    }
}
