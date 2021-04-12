package com.trixpert.beebbeeb.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterCountingResponse {
    private List<CountingResponse> carsOfType;
    private List<CountingResponse> carsOfBrand;
    private List<CountingResponse> carsOfModel;
    private List<CountingResponse> carsOfColor;
}
