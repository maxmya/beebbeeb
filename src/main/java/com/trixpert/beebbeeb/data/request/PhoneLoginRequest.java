package com.trixpert.beebbeeb.data.request;

import lombok.Data;


@Data
public class PhoneLoginRequest {
    private String phone;
    private String password;
}
