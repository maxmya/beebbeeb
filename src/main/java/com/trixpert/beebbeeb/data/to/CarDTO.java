package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTO {
    private long id;
    private LocalDateTime additionDate;
    private ModelDTO model;
    private CategoryDTO category;
    private ColorDTO color;
    private UserDTO creator;
    private List<PhotoDTO> photos;
    private boolean active;
}
