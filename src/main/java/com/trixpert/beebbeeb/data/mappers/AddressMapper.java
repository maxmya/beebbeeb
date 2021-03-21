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
                .title(addressDTO.getTitle())
                .latitude(addressDTO.getLatitude())
                .longitude(addressDTO.getLongitude())
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
                .active(addressEntity.isActive())
                .build();
    }
}
