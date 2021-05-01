package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.ModelEntity;
import com.trixpert.beebbeeb.data.to.ModelDTO;
import com.trixpert.beebbeeb.data.to.PhotoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelMapper {

    private final BrandMapper brandMapper;
    private final PhotoMapper photoMapper;

    public ModelMapper(BrandMapper brandMapper, PhotoMapper photoMapper) {
        this.brandMapper = brandMapper;
        this.photoMapper = photoMapper;
    }

    public ModelEntity convertToEntity(ModelDTO modelDTO) {
        return ModelEntity.builder()
                .id(modelDTO.getId())
                .name(modelDTO.getName())
                .englishName(modelDTO.getEnglishName())
                .brand(brandMapper.convertToEntity(modelDTO.getBrand()))
                .year(modelDTO.getYear())
                .active(modelDTO.isActive())
                .build();
    }

    public ModelDTO convertToDTO(ModelEntity modelEntity) {

        List<PhotoDTO> photoDTOS = new ArrayList<>();

        modelEntity.getPhotos().forEach(photoEntity -> {
            if (photoEntity.isActive())
                photoDTOS.add(photoMapper.convertToDTO(photoEntity));
        });

        return ModelDTO.builder()
                .id(modelEntity.getId())
                .name(modelEntity.getName())
                .photos(photoDTOS)
                .englishName(modelEntity.getEnglishName())
                .brand(brandMapper.convertToDTO(modelEntity.getBrand()))
                .year(modelEntity.getYear())
                .active(modelEntity.isActive())
                .build();
    }


}
