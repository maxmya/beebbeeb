package com.trixpert.beebbeeb.data.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchRegistrationRequest extends RegistrationRequest {
    private String BranchName;
    private String address;
    private long vendorId;
}