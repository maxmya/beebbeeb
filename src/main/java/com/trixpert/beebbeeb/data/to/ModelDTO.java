package com.trixpert.beebbeeb.data.to;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelDTO {
    private Long id;
    private BrandDTO brand;
    private List<PhotoDTO> photos;
    private String name;
    private String englishName;
    private String year;
    private boolean active;
}
