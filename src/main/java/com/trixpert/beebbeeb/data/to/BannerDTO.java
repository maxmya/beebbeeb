package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerDTO {
    private long id;
    private String name;
    private boolean main;
    private PhotoDTO photo;
}
