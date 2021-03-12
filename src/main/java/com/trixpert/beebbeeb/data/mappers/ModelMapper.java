package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.ModelEntity;
import com.trixpert.beebbeeb.data.to.ModelDTO;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    private  final  BrandMapper brandMapper ;

    public ModelMapper(BrandMapper brandMapper) {
        this.brandMapper = brandMapper;
    }

    public ModelEntity convertToEntity (ModelDTO modelDTO){
        return ModelEntity.builder()
                .id(modelDTO.getId())
                .name(modelDTO.getName())
                .brand(brandMapper.convertToEntity(modelDTO.getBrand()))
                .year(modelDTO.getYear())
                .active(modelDTO.isActive())
                .build();
    }

    public ModelDTO convertToDTO (ModelEntity modelEntity){
        return ModelDTO.builder()
                .id(modelEntity.getId())
                .name(modelEntity.getName())
                .brand(brandMapper.convertToDTO(modelEntity.getBrand()))
                .year(modelEntity.getYear())
                .active(modelEntity.isActive())
                .build();
    }


}
