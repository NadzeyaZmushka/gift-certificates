package com.epam.esm.hateoas;

import com.epam.esm.controller.impl.OrderControllerImpl;
import com.epam.esm.controller.impl.UserControllerImpl;
import com.epam.esm.dto.OrderCreateRequestDTO;
import com.epam.esm.dto.OrderDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderLinkBuilder {

    public void addLinksForOrder(OrderDTO orderDTO) {
        Link linkForSelf = linkTo(OrderControllerImpl.class)
                .slash(orderDTO.getId())
                .withSelfRel();
        Link linkForAll = linkTo(methodOn(OrderControllerImpl.class)
                .findAll(1, 10, null))
                .withRel("findAll");
        Link linkForCreateOrder = linkTo(methodOn(OrderControllerImpl.class)
                .create(new OrderCreateRequestDTO()))
                .withRel("create");
        Link linkForUser = linkTo(methodOn(UserControllerImpl.class)
                .findOne(orderDTO.getUserId()))
                .withRel("findUser");

        orderDTO.add(linkForSelf, linkForAll, linkForCreateOrder, linkForUser);
    }

    public void createPaginationLinks(PagedModel<OrderDTO> model, Long userId) {
        PagedModel.PageMetadata metadata = model.getMetadata();
        int curPage = (int) metadata.getNumber();
        int size = (int) metadata.getSize();
        int totalPages = (int) metadata.getTotalPages();
        if (curPage < totalPages) {
            model.add(linkTo(methodOn(OrderControllerImpl.class)
                    .findAll(curPage + 1, size, userId))
                    .withRel("nextPage"));
            model.add(linkTo(methodOn(OrderControllerImpl.class)
                    .findAll(totalPages, size, userId))
                    .withRel("lastPage"));
        }
        if (curPage > 1) {
            model.add(linkTo(methodOn(OrderControllerImpl.class)
                    .findAll(curPage - 1, size, userId))
                    .withRel("prevPage"));
        }
        model.add(linkTo(methodOn(OrderControllerImpl.class)
                .findAll(curPage, size, userId))
                .withSelfRel());
    }
}
