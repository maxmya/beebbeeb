package com.trixpert.beebbeeb.data.response;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponse {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
