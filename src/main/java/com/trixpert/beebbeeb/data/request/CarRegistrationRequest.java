package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRegistrationRequest {
    @NotNull(message = "model id can't be null")
    private long modelId;
    private String categoryName;
    private String colorName;
    private String colorCode;
    private long typeId;
    private long parentColorId;
}
