package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.BrandEntity;
import com.trixpert.beebbeeb.data.to.BrandDTO;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    public BrandEntity convertToEntity(BrandDTO brandDTO) {
        return BrandEntity.builder()
                .id(brandDTO.getId())
                .active(brandDTO.isActive())
                .description(brandDTO.getDescription())
                .logoUrl(brandDTO.getLogoUrl())
                .name(brandDTO.getName())
                .build();
    }


    public BrandDTO convertToDTO(BrandEntity brandEntity) {
        return BrandDTO.builder()
                .id(brandEntity.getId())
                .active(brandEntity.isActive())
                .origin(brandEntity.getOrigin())
                .description(brandEntity.getDescription())
                .logoUrl(brandEntity.getLogoUrl())
                .name(brandEntity.getName())
                .build();
    }

}
