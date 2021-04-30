package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.repositories.CarInstanceRepository;
import com.trixpert.beebbeeb.data.to.CarInstanceDTO;
import com.trixpert.beebbeeb.data.to.VendorDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VendorMapper {

    private final UserMapper userMapper;

    public VendorMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public VendorEntity convertToEntity(VendorDTO vendorDTO) {
        return VendorEntity.builder()
                .id(vendorDTO.getId())
                .manager(userMapper.convertToEntity(vendorDTO.getManager()))
                .name(vendorDTO.getVendorName())
                .mainAddress(vendorDTO.getMainAddress())
                .accountManagerName(vendorDTO.getAccountManagerName())
                .accountManagerPhone(vendorDTO.getAccountManagerPhone())
                .generalManagerName(vendorDTO.getGeneralManagerName())
                .generalManagerPhone(vendorDTO.getGeneralManagerPhone())
                .active(vendorDTO.isActive())
                .workingTime(vendorDTO.getWorkingTime())
                .build();
    }

    public VendorDTO convertToDTO(VendorEntity vendorEntity) {

        return VendorDTO.builder()
                .id(vendorEntity.getId())
                .manager(userMapper.convertToDTO(vendorEntity.getManager()))
                .vendorName(vendorEntity.getName())
                .mainAddress(vendorEntity.getMainAddress())
                .accountManagerName(vendorEntity.getAccountManagerName())
                .accountManagerPhone(vendorEntity.getAccountManagerPhone())
                .generalManagerName(vendorEntity.getGeneralManagerName())
                .generalManagerPhone(vendorEntity.getGeneralManagerPhone())
                .active(vendorEntity.isActive())
                .WorkingTime(vendorEntity.getWorkingTime())
                .build();
    }


}
