package com.trixpert.beebbeeb.data.response;

import com.trixpert.beebbeeb.data.to.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerProfileResponse {
    private long id;
    private String phone;
    private String displayName;
    private String profileUrl;
    private String horoscope;
    private AddressDTO address;
}
