package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    //todo messages
    @Email
    @NotBlank
    private String email;
    @Size(min = 6, max = 50)
    private String password;
    @NotBlank
    @Size(min = 2, max = 60)
    private String name;
    @NotBlank
    @Size(min = 2, max = 60)
    private String surname;
}
