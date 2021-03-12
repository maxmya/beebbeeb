package com.trixpert.beebbeeb.data.request;

import lombok.Data;

@Data
public class PhoneLoginRequest {
    String phone;
    String password;
}
