package com.trixpert.beebbeeb.data.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponse {
    private String displayName;
    private String accessToken;
    private String tokenType = "Bearer";
}