package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.PurchasingRequestEntity;
import com.trixpert.beebbeeb.data.to.PurchasingRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class PurchasingRequestMapper {
    private final VendorMapper vendorMapper;
    private final CustomerMapper customerMapper;
    private final CarInstanceMapper carInstanceMapper;

    public PurchasingRequestMapper(VendorMapper vendorMapper, CustomerMapper customerMapper,
                                   CarInstanceMapper carInstanceMapper) {
        this.vendorMapper = vendorMapper;
        this.customerMapper = customerMapper;
        this.carInstanceMapper = carInstanceMapper;
    }

    public PurchasingRequestEntity convertToEntity (PurchasingRequestDTO purchasingRequestDTO){
        return PurchasingRequestEntity.builder()
                .id(purchasingRequestDTO.getId())
                .status(purchasingRequestDTO.getStatus())
                .paymentType(purchasingRequestDTO.getPaymentType())
                .comment(purchasingRequestDTO.getComment())
                .date(purchasingRequestDTO.getDate())
                .vendor(vendorMapper.convertToEntity(purchasingRequestDTO.getVendor()))
                .customer(customerMapper.convertToEntity(purchasingRequestDTO.getCustomer()))
                .carInstance(carInstanceMapper.convertToEntity(
                        purchasingRequestDTO.getCarInstance()))
                .active(purchasingRequestDTO.isActive())
                .build();
    }
    public PurchasingRequestDTO convertToDTO(PurchasingRequestEntity purchasingRequestEntity){
        return PurchasingRequestDTO.builder()
                .id(purchasingRequestEntity.getId())
                .status(purchasingRequestEntity.getStatus())
                .paymentType(purchasingRequestEntity.getPaymentType())
                .comment(purchasingRequestEntity.getComment())
                .date(purchasingRequestEntity.getDate())
                .vendor(vendorMapper.convertToDTO(purchasingRequestEntity.getVendor()))
                .customer(customerMapper.convertToDTO(purchasingRequestEntity.getCustomer()))
                .carInstance(carInstanceMapper.convertToDTO(
                        purchasingRequestEntity.getCarInstance()))
                .active(purchasingRequestEntity.isActive())
                .build();
    }
}
