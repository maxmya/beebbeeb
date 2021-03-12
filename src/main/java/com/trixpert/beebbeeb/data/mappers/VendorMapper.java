package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.to.VendorDTO;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {


    public VendorEntity convertToEntity(VendorDTO vendorDTO) {
        return VendorEntity.builder()
                .id(vendorDTO.getId())
                .name(vendorDTO.getVendorName())
                .mainAddress(vendorDTO.getMainAddress())
                .accountManagerName(vendorDTO.getAccountManagerName())
                .accountManagerPhone(vendorDTO.getAccountManagerPhone())
                .generalManagerName(vendorDTO.getGeneralManagerName())
                .generalManagerPhone(vendorDTO.getGeneralManagerPhone())
                .active(vendorDTO.isActive())
                .build();
    }

    public VendorDTO convertToDTO(VendorEntity vendorEntity) {
        return VendorDTO.builder()
                .id(vendorEntity.getId())
                .vendorName(vendorEntity.getName())
                .mainAddress(vendorEntity.getMainAddress())
                .accountManagerName(vendorEntity.getAccountManagerName())
                .accountManagerPhone(vendorEntity.getAccountManagerPhone())
                .generalManagerName(vendorEntity.getGeneralManagerName())
                .generalManagerPhone(vendorEntity.getGeneralManagerPhone())
                .active(vendorEntity.isActive())
                .build();
    }


}
