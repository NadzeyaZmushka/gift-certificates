package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagDTO extends RepresentationModel<TagDTO> {

    private Long id;
    @Size(min = 2, max = 50, message = "{tag.incorrectNameLength}")
    @NotBlank(message = "{tag.incorrectName}")
    private String name;

}
