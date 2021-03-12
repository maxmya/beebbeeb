package com.trixpert.beebbeeb.data.request;


import com.trixpert.beebbeeb.data.to.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistraionRequest extends RegistrationRequest{

    private String preferredBank;

    private String jobTitle;

    private String jobAddress;

    private long income;
}
