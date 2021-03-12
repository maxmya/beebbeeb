package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.to.BrandDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelRegisterRequest {

    private long brandId ;

    private String name ;

    private String year ;


}
