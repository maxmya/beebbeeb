package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.to.CarDTO;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    private final ModelMapper modelMapper;
    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;
    private final ColorMapper colorMapper;
    private final UserMapper userMapper;

    public CarMapper(ModelMapper modelMapper,
                     CategoryMapper categoryMapper,
                     BrandMapper brandMapper,
                     ColorMapper colorMapper,
                     UserMapper userMapper) {

        this.modelMapper = modelMapper;
        this.categoryMapper = categoryMapper;
        this.brandMapper = brandMapper;
        this.colorMapper = colorMapper;
        this.userMapper = userMapper;
    }

    public CarEntity convertToEntity(CarDTO carDTO) {
        return CarEntity.builder()
                .id(carDTO.getId())
                .additionDate(carDTO.getAdditionDate())
                .creator(userMapper.convertToEntity(carDTO.getCreator()))
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
                .creator(userMapper.convertToDTO(carEntity.getCreator()))
                .model(modelMapper.convertToDTO(carEntity.getModel()))
                .category(categoryMapper.convertToDTO(carEntity.getCategory()))
                .color(colorMapper.convertToDTO(carEntity.getColor()))
                .active(carEntity.isActive())
                .build();
    }


}

