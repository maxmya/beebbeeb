package com.trixpert.beebbeeb.data.request;

import lombok.Data;

@Data
public class CarSpecsDeleteRequest {
    private long specsId;
    private boolean essential;
}
