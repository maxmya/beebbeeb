package com.trixpert.beebbeeb.data.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PhoneLoginRequest {
    private String phone;
    private String password;
}
