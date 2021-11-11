package com.epam.esm.hateoas;

import com.epam.esm.controller.impl.OrderControllerImpl;
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
                .findAll(1, 1000))
                .withRel("findAll");
        Link linkForCreateOrder = linkTo(methodOn(OrderControllerImpl.class)
                .create(null, null))
                .withRel("create");

        orderDTO.add(linkForSelf, linkForAll, linkForCreateOrder);
    }

    public void createPaginationLinks(PagedModel<OrderDTO> model) {
        PagedModel.PageMetadata metadata = model.getMetadata();
        int curPage = (int) metadata.getNumber();
        int size = (int) metadata.getSize();
        int totalPages = (int) metadata.getTotalPages();
        if (curPage < totalPages) {
            model.add(linkTo(methodOn(OrderControllerImpl.class)
                    .findAll(curPage + 1, size))
                    .withRel("nextPage"));
            model.add(linkTo(methodOn(OrderControllerImpl.class)
                    .findAll(totalPages, size))
                    .withRel("lastPage"));
        }
        if (curPage > 1) {
            model.add(linkTo(methodOn(OrderControllerImpl.class)
                    .findAll(curPage - 1, size))
                    .withRel("prevPage"));
        }
        model.add(linkTo(methodOn(OrderControllerImpl.class)
                .findAll(curPage, size))
                .withSelfRel());
    }
}
