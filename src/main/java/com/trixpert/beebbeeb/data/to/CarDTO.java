package com.trixpert.beebbeeb.data.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDTO {
    private long id;
    private String condition;
    private Date additionDate;
    private ModelDTO model;
    private BranchDTO branch;
    private CategoryDTO category;
    private ColorDTO color;
    private  boolean active;
}