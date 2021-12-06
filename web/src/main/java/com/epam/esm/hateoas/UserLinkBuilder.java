package com.epam.esm.hateoas;

import com.epam.esm.controller.impl.OrderControllerImpl;
import com.epam.esm.controller.impl.UserControllerImpl;
import com.epam.esm.dto.OrderCreateRequestDTO;
import com.epam.esm.dto.UserDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserLinkBuilder {

    public void addLinksForUser(UserDTO userDTO) {
        Link linkForSelf = linkTo(UserControllerImpl.class)
                .slash(userDTO.getId())
                .withSelfRel();
        Link linkForAll = linkTo(methodOn(UserControllerImpl.class)
                .findAll(1, 10))
                .withRel("findAll");
        Link linForCreateOrder = linkTo(methodOn(OrderControllerImpl.class)
                .create(new OrderCreateRequestDTO()))
                .withRel("createOrder");
        Link userOrders = linkTo(methodOn(UserControllerImpl.class)
                .findAllByUser(userDTO.getId(), 1, 10))
                .withRel("userOrders");

        userDTO.add(linkForSelf, linkForAll, linForCreateOrder, userOrders);
    }

    public void createPaginationLinks(PagedModel<UserDTO> model) {
        PagedModel.PageMetadata metadata = model.getMetadata();
        int curPage = (int) metadata.getNumber();
        int size = (int) metadata.getSize();
        int totalPages = (int) metadata.getTotalPages();
        if (curPage < totalPages) {
            model.add(linkTo(methodOn(UserControllerImpl.class)
                    .findAll(curPage + 1, size))
                    .withRel("nextPage"));
            model.add(linkTo(methodOn(UserControllerImpl.class)
                    .findAll(totalPages, size))
                    .withRel("lastPage"));
        }
        if (curPage > 1) {
            model.add(linkTo(methodOn(UserControllerImpl.class)
                    .findAll(curPage - 1, size))
                    .withRel("prevPage"));
        }
        model.add(linkTo(methodOn(UserControllerImpl.class)
                .findAll(curPage, size))
                .withSelfRel());
    }

}
