package com.trixpert.beebbeeb.data.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandRegisterRequest {
    @NotNull(message = "brand name can't be null")
    @Size(min = 2, max = 50, message = "brand name is between 2 and 50 char")
    private String name;
    private String origin;
    private String englishName;

}