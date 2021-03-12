package com.trixpert.beebbeeb.data.request;


import com.trixpert.beebbeeb.data.to.TypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRegistrationRequest {

    private String name;

    private TypeDTO type;
}
