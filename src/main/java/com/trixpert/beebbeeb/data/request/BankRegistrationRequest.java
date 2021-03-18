package com.trixpert.beebbeeb.data.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankRegistrationRequest extends RegistrationRequest {
    @NotNull(message = "bank name can't be null")
    @Size(min = 2, max = 50)
    private String bankName;

    private String bankLogoUrl;
}
