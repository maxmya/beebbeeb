package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.AddressEntity;
import com.trixpert.beebbeeb.data.to.AddressDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressEntity convertToEntity(AddressDTO addressDTO) {
        return AddressEntity.builder()
                .id(addressDTO.getId())
                .fullAddress(addressDTO.getFullAddress())
                .latitude(addressDTO.getLatitude())
                .longitude(addressDTO.getLongitude())
                .title(addressDTO.getTitle())
                .city(addressDTO.getCity())
                .governorate(addressDTO.getGovernorate())
                .apartmentNumber(addressDTO.getApartmentNumber())
                .building(addressDTO.getBuilding())
                .floor(addressDTO.getFloor())
                .landmark(addressDTO.getLandmark())
                .main(addressDTO.isPrimary())
                .street(addressDTO.getStreet())
                .type(addressDTO.getType())
                .active(addressDTO.isActive())
                .build();
    }


    public AddressDTO convertToDTO(AddressEntity addressEntity) {
        if (addressEntity == null) return null;
        return AddressDTO.builder()
                .id(addressEntity.getId())
                .fullAddress(addressEntity.getFullAddress())
                .latitude(addressEntity.getLatitude())
                .longitude(addressEntity.getLongitude())
                .title(addressEntity.getTitle())
                .city(addressEntity.getCity())
                .governorate(addressEntity.getGovernorate())
                .apartmentNumber(addressEntity.getApartmentNumber())
                .building(addressEntity.getBuilding())
                .floor(addressEntity.getFloor())
                .landmark(addressEntity.getLandmark())
                .primary(addressEntity.isMain())
                .street(addressEntity.getStreet())
                .type(addressEntity.getType())
                .active(addressEntity.isActive())
                .build();
    }
}
