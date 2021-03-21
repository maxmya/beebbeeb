package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.entites.PhotoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerRegistrationRequest {
    private String name;
    private boolean main;
}
