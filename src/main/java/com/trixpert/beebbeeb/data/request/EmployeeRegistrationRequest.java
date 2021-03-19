package com.trixpert.beebbeeb.data.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegistrationRequest extends RegistrationRequest {
    @NotNull(message = "title can't be null")
    private String title;

    @NotNull(message = "branch id can't be null")
    private long branchId;

}
