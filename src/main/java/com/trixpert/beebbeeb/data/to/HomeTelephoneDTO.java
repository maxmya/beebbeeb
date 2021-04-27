package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeTelephoneDTO {
    private long id ;
    private long vendorId;
    private String telephoneNumber;
    private boolean active;
}
