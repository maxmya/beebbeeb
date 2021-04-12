package com.trixpert.beebbeeb.data.response;


import com.trixpert.beebbeeb.data.to.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private long id;
    private String name;
    private String email;
    private String phone;
    private LinkableImage customerPhoto;
    private String preferredBank;
    private String jobTitle;
    private String jobAddress;
    private long income;
    private List<AddressDTO> addresses;
    private boolean active;
}

