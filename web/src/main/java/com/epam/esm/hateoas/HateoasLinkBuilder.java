package com.epam.esm.hateoas;

import com.epam.esm.controller.impl.CertificatesControllerImpl;
import com.epam.esm.controller.impl.OrderControllerImpl;
import com.epam.esm.controller.impl.TagControllerImpl;
import com.epam.esm.controller.impl.UserControllerImpl;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
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
                .withSelfRel();
        Link findAll = linkTo(methodOn(TagControllerImpl.class)
                .findAll(1, 1000))
                .withRel("findAll");

        tagDTO.add(linkForSelf, findAll);
    }

    public void addLinksForCertificate(CertificateDTO certificateDTO) {
        Link linkForSelf = linkTo(CertificatesControllerImpl.class)
                .slash(certificateDTO.getId())
                .withSelfRel();
        Link findAll = linkTo(methodOn(CertificatesControllerImpl.class)
                .findAll(null, "", "", "", 1, 1000))
                .withRel("findAll");

        certificateDTO.add(linkForSelf, findAll);
    }

    public void addLinksForUser(UserDTO userDTO) {
        Link linkForSelf = linkTo(UserControllerImpl.class)
                .slash(userDTO.getId())
                .withSelfRel();
        Link linkForUserOrders = linkTo(methodOn(OrderControllerImpl.class)
                .findAllByUserId(userDTO.getId()))
                .withRel("Show user's orders");
        Link linkForAll = linkTo(methodOn(UserControllerImpl.class)
                .findAll(1, 1000))
                .withRel("Show all users");
        Link linForCreateOrder = linkTo(methodOn(UserControllerImpl.class)
                .createOrder(userDTO.getId(), null))
                .withRel("Create order");

        userDTO.add(linkForSelf, linkForUserOrders, linkForAll, linForCreateOrder);
    }

    public void addLinksForOrder(OrderDTO orderDTO) {
        Link linkForSelf = linkTo(OrderControllerImpl.class)
                .slash(orderDTO.getId())
                .withSelfRel();
        Link linkForAll = linkTo(methodOn(OrderControllerImpl.class)
                .findAll(1, 1000))
                .withRel("findAll");
        Link linkForCreateOrder = linkTo(methodOn(UserControllerImpl.class)
                .createOrder(null, null))
                .withRel("Create order");

        orderDTO.add(linkForSelf, linkForAll, linkForCreateOrder);
    }

}
