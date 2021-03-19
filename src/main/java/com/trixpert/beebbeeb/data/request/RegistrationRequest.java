package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotNull(message = "name can't be null")
    private String name;

    @NotNull(message = "phone number can't be null")
    @Pattern(message = "phone number must be a number", regexp = "^[0-9]*$")
    @Size(min = 10, max = 11)
    private String phone;

    @Email
    private String email;

    private boolean active;

    @NotNull(message = "password can't be null")
    @Size(min = 6, max = 255 ,message = "password is between 6 and 255 char")
    private String password;
}
