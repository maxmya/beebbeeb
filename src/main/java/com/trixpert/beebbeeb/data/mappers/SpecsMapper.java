package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.EssentialSpecsEntity;
import com.trixpert.beebbeeb.data.entites.ExtraSpecsEntity;
import com.trixpert.beebbeeb.data.to.SpecsDTO;
import org.springframework.stereotype.Component;

@Component
public class SpecsMapper {


    public SpecsDTO convertToDTO(EssentialSpecsEntity specsEntity) {
        return SpecsDTO.builder()
                .key(specsEntity.getKey())
                .value(specsEntity.getValue())
                .carId(specsEntity.getCar().getId())
                .build();
    }


    public SpecsDTO convertToDTO(ExtraSpecsEntity specsEntity) {
        return SpecsDTO.builder()
                .key(specsEntity.getKey())
                .value(specsEntity.getValue())
                .carId(specsEntity.getCar().getId())
                .build();
    }

}
