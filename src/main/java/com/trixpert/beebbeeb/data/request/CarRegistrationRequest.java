package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.to.CategoryDTO;
import com.trixpert.beebbeeb.data.to.ColorDTO;
import com.trixpert.beebbeeb.data.to.TypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRegistrationRequest {
    @NotNull(message = "model id can't be null")
    private long modelId;
    private CategoryDTO category;
    private ColorDTO color;
    private long typeId;
    private long parentColorId;
}
