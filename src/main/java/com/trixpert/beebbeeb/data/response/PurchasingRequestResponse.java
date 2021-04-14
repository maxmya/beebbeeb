package com.trixpert.beebbeeb.data.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchasingRequestResponse {
    private long id;
    private String status;
    private String paymentType;
    private String comment;
    private Date date;
    private String vendorName;
    private long customerId;
    private String customerName;
    private String carBrand;
    private String carModel;
    private boolean active;
}
