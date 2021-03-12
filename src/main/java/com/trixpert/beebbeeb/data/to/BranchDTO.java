package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class BranchDTO {
    private Long id;
    private String name;
    private String address;
    private UserDTO branchManager;
    private VendorDTO vendor;
    private int numberOfEmployees;
    private boolean active;
}
