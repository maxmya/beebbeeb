package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.BranchEntity;
import com.trixpert.beebbeeb.data.to.BranchDTO;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    private final VendorMapper vendorMapper;
    private final UserMapper userMapper;

    public BranchMapper(VendorMapper vendorMapper,
                        UserMapper userMapper) {
        this.vendorMapper = vendorMapper;
        this.userMapper = userMapper;
    }

    public BranchEntity convertToEntity(BranchDTO branchDTO) {
        return BranchEntity.builder()
                .id(branchDTO.getId())
                .address(branchDTO.getAddress())
                .name(branchDTO.getName())
                .user(userMapper.convertToEntity(branchDTO.getBranchManager()))
                .vendor(vendorMapper.convertToEntity(branchDTO.getVendor()))
                .active(branchDTO.isActive())
                .build();
    }

    public BranchDTO convertToDTO(BranchEntity branchEntity) {
        return BranchDTO.builder()
                .id(branchEntity.getId())
                .address(branchEntity.getAddress())
                .name(branchEntity.getName())
                .vendor(vendorMapper.convertToDTO(branchEntity.getVendor()))
                .branchManager(userMapper.convertToDTO(branchEntity.getUser()))
                .active(branchEntity.isActive())
                .build();
    }


}
