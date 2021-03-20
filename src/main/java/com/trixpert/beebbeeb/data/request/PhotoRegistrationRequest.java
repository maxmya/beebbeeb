package com.trixpert.beebbeeb.data.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoRegistrationRequest {

    private boolean interior;

    private boolean mainPhoto;

    @NotNull
    private String photoUrl;

    @Size(min = 2, max = 50 , message = "caption is between 2 and 50 char")
    private String caption;

    private String description;
}

