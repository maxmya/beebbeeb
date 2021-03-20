package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.to.CarDTO;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    private final ModelMapper modelMapper;
    private final CategoryMapper categoryMapper;
    private final ColorMapper colorMapper;

    public CarMapper(ModelMapper modelMapper,
                     CategoryMapper categoryMapper,
                     ColorMapper colorMapper) {
        this.modelMapper = modelMapper;
        this.categoryMapper = categoryMapper;
        this.colorMapper = colorMapper;
    }

    public CarEntity convertToEntity(CarDTO carDTO) {
        return CarEntity.builder()
                .id(carDTO.getId())
                .additionDate(carDTO.getAdditionDate())
                .model(modelMapper.convertToEntity(carDTO.getModel()))
                .category(categoryMapper.convertToEntity(carDTO.getCategory()))
                .color(colorMapper.convertToEntity(carDTO.getColor()))
                .active(carDTO.isActive())
                .build();
    }

    public CarDTO convertToDTO(CarEntity carEntity) {
        return CarDTO.builder()
                .id(carEntity.getId())
                .additionDate(carEntity.getAdditionDate())
                .model(modelMapper.convertToDTO(carEntity.getModel()))
                .category(categoryMapper.convertToDTO(carEntity.getCategory()))
                .color(colorMapper.convertToDTO(carEntity.getColor()))
                .active(carEntity.isActive())
                .build();
    }


}

