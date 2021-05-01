package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesManDTO {
    private long id ;
    private long vendorId;
    private String name;
    private String phone;
    private boolean active;
}
