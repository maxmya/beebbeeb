package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerMobileRegistrationRequest {
    private String name;
    private String password;
    private String phone;
    private String horoscope;
}
