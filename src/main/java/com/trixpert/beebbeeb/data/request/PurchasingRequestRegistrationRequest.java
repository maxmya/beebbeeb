package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchasingRequestRegistrationRequest {
    private String status;
    private String paymentType;
    private String comment;
    private Date date;
    private long vendorId;
    private long customerId;
    private long carInstanceId;
    private boolean active;
}
