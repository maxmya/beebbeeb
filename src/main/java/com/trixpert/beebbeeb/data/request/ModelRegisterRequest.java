package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelRegisterRequest {
    @NotNull(message = "branch id can't be null")
    private long brandId;
    @NotNull(message = "model name can't be null")
    private String name;
    private String englishName;
    @NotNull(message = "model year can't be null")
    private String year;
}
