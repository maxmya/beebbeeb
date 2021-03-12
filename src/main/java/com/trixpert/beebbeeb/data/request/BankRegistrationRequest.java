package com.trixpert.beebbeeb.data.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankRegistrationRequest extends RegistrationRequest {
    private String bankName;
    private String bankLogoUrl;
}
