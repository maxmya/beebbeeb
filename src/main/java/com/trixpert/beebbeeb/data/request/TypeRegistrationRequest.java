package com.trixpert.beebbeeb.data.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TypeRegistrationRequest {
    @NotNull(message = "type name can't be null")
    @Size(min = 2, max = 50)
    private String name;

    private String description;
}
