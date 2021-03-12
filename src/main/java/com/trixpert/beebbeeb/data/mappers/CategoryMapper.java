package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.CategoryEntity;
import com.trixpert.beebbeeb.data.to.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

   private final TypeMapper typeMapper ;

    public CategoryMapper(TypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }

    public CategoryEntity convertToEntity(CategoryDTO categoryDTO){
        return CategoryEntity.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .type(typeMapper.convertToEntity(categoryDTO.getType()))
                .active(categoryDTO.isActive())
                .build();
    }

    public CategoryDTO convertToDTO (CategoryEntity categoryEntity){
        return CategoryDTO.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .type(typeMapper.convertToDTO(categoryEntity.getType()))
                .active(categoryEntity.isActive())
                .build();
    }
}
