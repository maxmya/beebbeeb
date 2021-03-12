package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.ColorEntity;
import com.trixpert.beebbeeb.data.to.ColorDTO;
import org.springframework.stereotype.Component;

@Component
public class ColorMapper {
    private final ParentColorMapper parentColorMapper;

    public ColorMapper(ParentColorMapper parentColorMapper) {
        this.parentColorMapper = parentColorMapper;
    }

    public ColorEntity convertToEntity(ColorDTO colorDTO){
        return ColorEntity.builder()
                .id(colorDTO.getId())
                .code(colorDTO.getCode())
                .name(colorDTO.getName())
                .active(colorDTO.isActive())
                .parentColor(parentColorMapper.convertToEntity(colorDTO.getParentColor()))
                .build();
    }
    public ColorDTO convertToDTO(ColorEntity colorEntity){
        return ColorDTO.builder()
                .id(colorEntity.getId())
                .name(colorEntity.getName())
                .code(colorEntity.getCode())
                .active(colorEntity.isActive())
                .parentColor(parentColorMapper.convertToDTO(colorEntity.getParentColor()))
                .build();
    }
}
