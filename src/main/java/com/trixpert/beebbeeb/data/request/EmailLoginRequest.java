package com.trixpert.beebbeeb.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailLoginRequest {
    @Email
    private String email;

    @NotNull(message = "password can't be null")
    @Size(min = 6, max = 255 ,message = "password is between 6 and 255 char")
    private String password;
}
