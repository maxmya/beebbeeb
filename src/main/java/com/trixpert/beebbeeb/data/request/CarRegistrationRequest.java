package com.trixpert.beebbeeb.data.request;

import com.trixpert.beebbeeb.data.to.ParentColorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRegistrationRequest {

    @NotNull(message = "model id can't be null")
    private long modelId;
    private String categoryName;
    private String colorName;
    private String colorCode;
    private String originalPrice;
    private long typeId;
    private long parentColorId;
    private List<ColorFormData> colors;

    @Data
    public static class ColorFormData {
        private ParentColorDTO parentColor;
        private String colorName;
        private String colorCode;
    }
}
