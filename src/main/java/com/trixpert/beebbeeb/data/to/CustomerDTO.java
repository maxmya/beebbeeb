package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private Long id;

    private UserDTO user;

    private String preferredBank;

    private String jobTitle;

    private String jobAddress;

    private long income;

    private boolean active;

}
