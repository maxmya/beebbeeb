package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String title;
    private UserDTO user;
    private BranchDTO branch;
    private boolean active;
}
