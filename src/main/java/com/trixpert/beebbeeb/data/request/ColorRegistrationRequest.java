package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.to.ParentColorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ColorRegistrationRequest {
    private String code;

    private String name;

    private long parentColorId;

    private boolean active;

}
