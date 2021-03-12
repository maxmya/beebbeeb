package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.to.CarDTO;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    private final ModelMapper modelMapper;
    private final BranchMapper branchMapper;
    private final CategoryMapper categoryMapper;
    private final ColorMapper colorMapper;

    public CarMapper(ModelMapper modelMapper,
                     BranchMapper branchMapper,
                     CategoryMapper categoryMapper,
                     ColorMapper colorMapper) {
        this.modelMapper = modelMapper;
        this.branchMapper = branchMapper;
        this.categoryMapper = categoryMapper;
        this.colorMapper = colorMapper;
    }

    public CarEntity convertToEntity(CarDTO carDTO){
        return CarEntity.builder()
                .id(carDTO.getId())
                .condition(carDTO.getCondition())
                .additionDate(carDTO.getAdditionDate())
                .model(modelMapper.convertToEntity(carDTO.getModel()))
                .branch(branchMapper.convertToEntity(carDTO.getBranch()))
                .category(categoryMapper.convertToEntity(carDTO.getCategory()))
                .color(colorMapper.convertToEntity(carDTO.getColor()))
                .active(carDTO.isActive())
                .build();
    }

    public CarDTO convertToDTO(CarEntity carEntity){
        return CarDTO.builder()
                .id(carEntity.getId())
                .condition(carEntity.getCondition())
                .additionDate(carEntity.getAdditionDate())
                .model(modelMapper.convertToDTO(carEntity.getModel()))
                .branch(branchMapper.convertToDTO(carEntity.getBranch()))
                .category(categoryMapper.convertToDTO(carEntity.getCategory()))
                .color(colorMapper.convertToDTO(carEntity.getColor()))
                .active(carEntity.isActive())
                .build();
    }
    

}

