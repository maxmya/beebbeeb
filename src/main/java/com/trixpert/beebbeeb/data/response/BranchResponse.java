package com.trixpert.beebbeeb.data.response;

import lombok.Data;

@Data
public class BranchResponse {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
