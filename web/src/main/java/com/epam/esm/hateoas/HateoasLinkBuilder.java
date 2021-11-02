package com.epam.esm.hateoas;

import com.epam.esm.controller.impl.CertificatesControllerImpl;
import com.epam.esm.controller.impl.TagControllerImpl;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasLinkBuilder {

    public void addLinksForTag(TagDTO tagDTO) {
        Link linkForSelf = linkTo(TagControllerImpl.class)
                .slash(tagDTO.getId())
                .withRel("Go to this tag");
        Link deleteLink = linkTo(methodOn(TagControllerImpl.class)
                .delete(tagDTO.getId()))
                .withRel("Delete this tag");

        tagDTO.add(linkForSelf, deleteLink);
    }

    public void addLinksForCertificate(CertificateDTO certificateDTO) {
        Link linkForSelf = linkTo(CertificatesControllerImpl.class)
                .slash(certificateDTO.getId())
                .withRel("Go to this certificate");
        Link deleteLink = linkTo(methodOn(CertificatesControllerImpl.class)
        .delete(certificateDTO.getId()))
                .withRel("Delete this certificate");
        Link updateLink = linkTo(methodOn(CertificatesControllerImpl.class)
        .update(certificateDTO.getId(), certificateDTO))
                .withRel("Update this certificate");

        certificateDTO.add(linkForSelf, deleteLink, updateLink);
    }

}
