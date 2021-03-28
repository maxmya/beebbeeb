package com.trixpert.beebbeeb.data.to;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.entites.PriceEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchasingRequestDTO {
    private Long id;

    private String status;

    private String payment_type;

    private String comment;

    private Date date;
    private VendorEntity vendor;
    private CustomerEntity customer;
    private CarInstanceEntity carInstanceEntity;

    private boolean active;



}
