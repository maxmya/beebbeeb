package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.to.ParentColorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ColorRegistrationRequest {

    private String code;

    @NotNull(message = "color name can't be added with ")
    private String name;

    @NotNull(message = "color can't be added without parent color ")
    private long parentColorId;

    private boolean active;

}
