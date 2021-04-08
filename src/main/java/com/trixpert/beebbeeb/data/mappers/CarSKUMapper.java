package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.CarSKUHolderEntity;
import com.trixpert.beebbeeb.data.to.CarSKUHolderDTO;
import org.springframework.stereotype.Component;

@Component
public class CarSKUMapper {

    public CarSKUHolderEntity convertToEntity(CarSKUHolderDTO carSKUHolderDTO) {
        return CarSKUHolderEntity.builder()
                .sku(carSKUHolderDTO.getSku())
                .id(carSKUHolderDTO.getId())
                .status(carSKUHolderDTO.getStatus())
                .build();

    }

    public CarSKUHolderDTO convertToDTO(CarSKUHolderEntity carSKUHolderEntity) {
        return CarSKUHolderDTO.builder()
                .sku(carSKUHolderEntity.getSku())
                .id(carSKUHolderEntity.getId())
                .status(carSKUHolderEntity.getStatus())
                .build();
    }


}
