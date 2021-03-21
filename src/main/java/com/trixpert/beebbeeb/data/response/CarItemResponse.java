package com.trixpert.beebbeeb.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CarItemResponse {
    private long id;
    private String name;
    private String image;
    private String price;
    private String currency;
    private int rating;
}
