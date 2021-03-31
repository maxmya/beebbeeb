package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.entites.PriceEntity;
import com.trixpert.beebbeeb.data.to.CarInstanceDTO;
import com.trixpert.beebbeeb.data.to.PriceDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarInstanceMapper {

    private final BranchMapper branchMapper;
    private final VendorMapper vendorMapper;
    private final CarMapper carMapper;
    private final PriceMapper priceMapper;

    public CarInstanceMapper(BranchMapper branchMapper,
                             VendorMapper vendorMapper,
                             CarMapper carMapper,
                             PriceMapper priceMapper) {

        this.branchMapper = branchMapper;
        this.vendorMapper = vendorMapper;
        this.carMapper = carMapper;
        this.priceMapper = priceMapper;
    }

    public CarInstanceDTO convertToDTO(CarInstanceEntity carInstanceEntity) {

        List<PriceDTO> prices = new ArrayList<>();

        carInstanceEntity.getPrices().forEach(price -> prices.add(priceMapper.convertToDTO(price)));

        return CarInstanceDTO.builder()
                .car(carMapper.convertToDTO(carInstanceEntity.getCar()))
                .branch(branchMapper.convertToDTO(carInstanceEntity.getBranch()))
                .vendor(vendorMapper.convertToDTO(carInstanceEntity.getVendor()))
                .id(carInstanceEntity.getId())
                .prices(prices)
                .active(carInstanceEntity.isActive())
                .condition(carInstanceEntity.getCondition())
                .originalPrice(carInstanceEntity.getOriginalPrice())
                .build();

    }
    public CarInstanceEntity convertToEntity(CarInstanceDTO carInstanceDTO){
        List<PriceEntity> prices = new ArrayList<>();
        carInstanceDTO.getPrices().forEach(price -> prices.add(priceMapper.convertToEntity(price)));
    return CarInstanceEntity.builder()
            .car(carMapper.convertToEntity(carInstanceDTO.getCar()))
            .branch(branchMapper.convertToEntity(carInstanceDTO.getBranch()))
            .vendor(vendorMapper.convertToEntity(carInstanceDTO.getVendor()))
            .id(carInstanceDTO.getId())
            .prices(prices)
            .active(carInstanceDTO.isActive())
            .condition(carInstanceDTO.getCondition())
            .originalPrice(carInstanceDTO.getOriginalPrice())
            .build();
    }


}
