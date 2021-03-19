package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMobileRegistrationRequest {
    @NotNull(message = "name can't be null")
    @Size(min = 2, max = 50)
    private String name;

    @NotNull(message = "password can't be null")
    @Size(min = 6, max = 255, message = "password is between 6 and 255 char")
    private String password;

    @NotNull(message = "phone number can't be null")
    @Pattern(message = "phone number must be a number", regexp = "^[0-9]*$")
    @Size(min = 10, max = 11)
    private String phone;

    private String horoscope;
}