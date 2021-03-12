package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandDTO {
    private Long id;
    private String name;
    private String origin;
    private String logoUrl;
    private String description;
    private boolean active;
}
