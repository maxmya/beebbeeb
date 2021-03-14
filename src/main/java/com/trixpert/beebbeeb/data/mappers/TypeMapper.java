package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.TypeEntity;
import com.trixpert.beebbeeb.data.to.TypeDTO;
import org.springframework.stereotype.Component;

@Component
public class TypeMapper {

    public TypeEntity convertToEntity(TypeDTO typeDTO) {
        return TypeEntity.builder()
                .id(typeDTO.getId())
                .name(typeDTO.getName())
                .description(typeDTO.getDescription())
                .logoUrl(typeDTO.getLogoUrl())
                .active(typeDTO.isActive())
                .build();
    }

    public TypeDTO convertToDTO(TypeEntity typeEntity) {
        return TypeDTO.builder()
                .id(typeEntity.getId())
                .name(typeEntity.getName())
                .description(typeEntity.getDescription())
                .logoUrl(typeEntity.getLogoUrl())
                .active(typeEntity.isActive())
                .build();
    }
}
