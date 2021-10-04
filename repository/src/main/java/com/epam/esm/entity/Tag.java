package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends BaseEntity {

    private String name;
//    private List<Certificate> certificates;
//
//    public void addCertificate(Certificate certificate) {
//        if (certificates == null) {
//            certificates = new ArrayList<>();
//        }
//        certificates.add(certificate);
//    }

}
