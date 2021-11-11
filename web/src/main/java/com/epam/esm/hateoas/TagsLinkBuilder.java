package com.epam.esm.hateoas;

import com.epam.esm.controller.impl.TagControllerImpl;
import com.epam.esm.dto.TagDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagsLinkBuilder {

    public void addLinksForTag(TagDTO tagDTO) {
        Link linkForSelf = linkTo(TagControllerImpl.class)
                .slash(tagDTO.getId())
                .withSelfRel();
        Link findAll = linkTo(methodOn(TagControllerImpl.class)
                .findAll(1, 1000))
                .withRel("findAll");

        tagDTO.add(linkForSelf, findAll);
    }

    public void createPaginationLinks(PagedModel<TagDTO> model) {
        PagedModel.PageMetadata metadata = model.getMetadata();
        int curPage = (int) metadata.getNumber();
        int size = (int) metadata.getSize();
        int totalPages = (int) metadata.getTotalPages();
        if (curPage < totalPages) {
            model.add(linkTo(methodOn(TagControllerImpl.class)
                    .findAll(curPage + 1, size))
                    .withRel("nextPage"));
            model.add(linkTo(methodOn(TagControllerImpl.class)
                    .findAll(totalPages, size))
                    .withRel("lastPage"));
        }
        if (curPage > 1) {
            model.add(linkTo(methodOn(TagControllerImpl.class)
                    .findAll(curPage - 1, size))
                    .withRel("prevPage"));
        }
        model.add(linkTo(methodOn(TagControllerImpl.class)
                .findAll(curPage, size))
                .withSelfRel());
    }

}
