package com.epam.esm.hateoas;

import com.epam.esm.controller.impl.CertificatesControllerImpl;
import com.epam.esm.dto.CertificateDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateLinkBuilder {

    public void addLinksForCertificate(CertificateDTO certificateDTO) {
        Link linkForSelf = linkTo(CertificatesControllerImpl.class)
                .slash(certificateDTO.getId())
                .withSelfRel();
        Link findAll = linkTo(methodOn(CertificatesControllerImpl.class)
                .findAll(null, null, null, null, 1, 10))
                .withRel("findAll");

        certificateDTO.add(linkForSelf, findAll);
    }

    public void createPaginationLinks(PagedModel<CertificateDTO> model, List<String> tagNames,
                                      String partName, String sortBy, String order) {
        PagedModel.PageMetadata metadata = model.getMetadata();
        int curPage = (int) metadata.getNumber();
        int size = (int) metadata.getSize();
        int totalPages = (int) metadata.getTotalPages();
        if (curPage < totalPages) {
            model.add(linkTo(methodOn(CertificatesControllerImpl.class)
                    .findAll(tagNames, partName, sortBy, order, curPage + 1, size))
                    .withRel("nextPage"));
            model.add(linkTo(methodOn(CertificatesControllerImpl.class)
                    .findAll(tagNames, partName, sortBy, order, totalPages, size))
                    .withRel("lastPage"));
        }
        if (curPage > 1) {
            model.add(linkTo(methodOn(CertificatesControllerImpl.class)
                    .findAll(tagNames, partName, sortBy, order, curPage - 1, size))
                    .withRel("prevPage"));
        }
        model.add(linkTo(methodOn(CertificatesControllerImpl.class)
                .findAll(tagNames, partName, sortBy, order, curPage, size))
                .withSelfRel());
    }
}
