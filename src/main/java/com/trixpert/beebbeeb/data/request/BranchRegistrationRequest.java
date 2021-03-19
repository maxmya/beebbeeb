package com.trixpert.beebbeeb.data.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchRegistrationRequest extends RegistrationRequest {
    @NotNull(message = "Branch name can't be null")
    @Size(min = 2, max = 50)
    private String BranchName;

    @NotNull(message = "branch address can't be null")
    @Size(min = 2, max = 50)
    private String address;

    @NotNull(message = "vendor id can't be null")
    @Pattern(message = "longitude must be a number", regexp = "^[0-9]*$")
    private long vendorId;
}