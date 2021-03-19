package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRegistrationRequest {
    @NotNull(message = "additional date can't be null")
    private Date additionDate;

    @NotNull(message = "model id can't be null")
    private long modelId;

    @NotNull(message = "branch id can't be null")
    private long branchId;

    @NotNull(message = "category id can't be null")
    private long categoryId;

    @NotNull(message = "color id can't be null")
    private long colorId;
}
