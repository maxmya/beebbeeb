package com.trixpert.beebbeeb.data.request;

import lombok.Data;

import java.util.List;

@Data
public class CarSpecsRequest {
    private long carId;
    private boolean essential;
    private String key;
    private String value;
}
