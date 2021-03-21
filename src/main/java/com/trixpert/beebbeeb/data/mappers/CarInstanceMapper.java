package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.to.CarInstanceDTO;
import org.springframework.stereotype.Component;

@Component
public class CarInstanceMapper {

    private final BranchMapper branchMapper;
    private final VendorMapper vendorMapper;
    private final CarMapper carMapper;

    public CarInstanceMapper(BranchMapper branchMapper,
                             VendorMapper vendorMapper,
                             CarMapper carMapper) {

        this.branchMapper = branchMapper;
        this.vendorMapper = vendorMapper;
        this.carMapper = carMapper;
    }

    public CarInstanceDTO convertToDTO(CarInstanceEntity carInstanceEntity) {
        return CarInstanceDTO.builder()
                .car(carMapper.convertToDTO(carInstanceEntity.getCar()))
                .branch(branchMapper.convertToDTO(carInstanceEntity.getBranch()))
                .vendor(vendorMapper.convertToDTO(carInstanceEntity.getVendor()))
                .id(carInstanceEntity.getId())
                .condition(carInstanceEntity.getCondition())
                .originalPrice(carInstanceEntity.getOriginalPrice())
                .build();

    }


}
