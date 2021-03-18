package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRegistrationRequest {
    private Date additionDate;
    private long modelId;
    private long branchId;
    private long categoryId;
    private long colorId;
}
