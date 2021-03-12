package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.to.BranchDTO;
import com.trixpert.beebbeeb.data.to.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegistrationRequest {
    private String title;
    private UserDTO user;
    private BranchDTO branch;
    private boolean active;
    private String password;
}
