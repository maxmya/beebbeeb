package com.trixpert.beebbeeb.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountingResponse {
    private String title;
    private int count;
}
