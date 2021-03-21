package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.PhotoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarMapper {

    private final ModelMapper modelMapper;
    private final CategoryMapper categoryMapper;
    private final ColorMapper colorMapper;
    private final UserMapper userMapper;
    private final PhotoMapper photoMapper;

    public CarMapper(ModelMapper modelMapper,
                     CategoryMapper categoryMapper,
                     ColorMapper colorMapper,
                     UserMapper userMapper,
                     PhotoMapper photoMapper) {

        this.modelMapper = modelMapper;
        this.categoryMapper = categoryMapper;
        this.colorMapper = colorMapper;
        this.userMapper = userMapper;
        this.photoMapper = photoMapper;
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

        List<PhotoDTO> photoDTOS = new ArrayList<>();

        carEntity.getPhotos().forEach(photoEntity -> {
            if (photoEntity.isActive())
                photoDTOS.add(photoMapper.convertToDTO(photoEntity));
        });

        return CarDTO.builder()
                .id(carEntity.getId())
                .photos(photoDTOS)
                .additionDate(carEntity.getAdditionDate())
                .creator(userMapper.convertToDTO(carEntity.getCreator()))
                .model(modelMapper.convertToDTO(carEntity.getModel()))
                .category(categoryMapper.convertToDTO(carEntity.getCategory()))
                .color(colorMapper.convertToDTO(carEntity.getColor()))
                .active(carEntity.isActive())
                .build();
    }


}

