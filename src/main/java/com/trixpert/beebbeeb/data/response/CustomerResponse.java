package com.trixpert.beebbeeb.data.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private long id;
    private String name;
    private String email;
    private String phone;
    private String preferredBank;
    private String jobTitle;
    private String jobAddress;
    private long income;
    private boolean active;
}

