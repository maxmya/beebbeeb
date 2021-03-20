package com.trixpert.beebbeeb.data.response;

import lombok.Data;

import java.util.List;

@Data
public class CarSpecsResponse {
    private List<String> keys;
    private List<String> values;
}
