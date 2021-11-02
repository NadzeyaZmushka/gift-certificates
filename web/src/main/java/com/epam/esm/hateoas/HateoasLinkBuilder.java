package com.epam.esm.hateoas;

import com.epam.esm.controller.impl.CertificatesControllerImpl;
import com.epam.esm.controller.impl.OrderControllerImpl;
import com.epam.esm.controller.impl.TagControllerImpl;
import com.epam.esm.controller.impl.UserControllerImpl;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

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

    public void addLinksForUser(UserDTO userDTO) {
        Link linkForSelf = linkTo(UserControllerImpl.class)
                .slash(userDTO.getId())
                .withRel("Go to this user");
        Link linkForUserOrders = linkTo(methodOn(OrderControllerImpl.class)
                .findAllByUserId(userDTO.getId()))
                .withRel("Show user's orders");
        Link linkForAll = linkTo(methodOn(UserControllerImpl.class)
                .findAll(0, 0))
                .withRel("Show all users");
        Link linkForAllWithOrders = linkTo(methodOn(UserControllerImpl.class)
                .findAllWithOrders(0, 0))
                .withRel("Show all users and their orders");
        Link linForCreateOrder = linkTo(methodOn(UserControllerImpl.class)
                .createOrder(userDTO.getId(), null))
                .withRel("Go to create order");
        userDTO.add(linkForSelf, linkForUserOrders, linkForAll, linkForAllWithOrders, linForCreateOrder);
    }

}
