package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {
    private long id;
    private boolean interior;
    private boolean mainPhoto;
    private String photoUrl;
    private String caption;
    private String description;
}
