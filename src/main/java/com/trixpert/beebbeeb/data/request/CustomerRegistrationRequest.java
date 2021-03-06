package com.trixpert.beebbeeb.data.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationRequest extends RegistrationRequest {
    private String preferredBank;
    private String jobTitle;
    private String jobAddress;
    private long income;
}
