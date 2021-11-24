package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "users")
public class UserDTO extends RepresentationModel<UserDTO> {

    private Long id;
    @Size(min = 2, max = 50, message = "")
    @NotBlank
    private String name;
    @Size(min = 2, max = 50, message = "")
    @NotBlank
    private String surname;
    @Email
    private String email;

}
