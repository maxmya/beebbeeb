package com.trixpert.beebbeeb.data.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PhoneLoginRequest {

    @NotNull(message = "phone number can't be null")
    @Pattern(message = "phone number must be a number", regexp = "^[0-9]*$")
    @Size(min = 10, max = 11)
    private String phone;

    @NotNull(message = "password can't be null")
    @Size(min = 6, max = 255 ,message = "password is between 6 and 255 char")
    private String password;
}
