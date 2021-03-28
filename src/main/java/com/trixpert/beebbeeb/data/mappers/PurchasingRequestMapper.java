package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.PurchasingRequestEntity;
import com.trixpert.beebbeeb.data.to.PurchasingRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class PurchasingRequestMapper {
    public PurchasingRequestEntity convertToEntity (PurchasingRequestDTO purchasingRequestDTO){
        return PurchasingRequestEntity.builder()
                .id(purchasingRequestDTO.getId())
                .status(purchasingRequestDTO.getStatus())
                .payment_type(purchasingRequestDTO.getPayment_type())
                .comment(purchasingRequestDTO.getComment())
                .date(purchasingRequestDTO.getDate())
                .vendor(purchasingRequestDTO.getVendor())
                .customer(purchasingRequestDTO.getCustomer())
                .carInstanceEntity(purchasingRequestDTO.getCarInstanceEntity())
                .active(purchasingRequestDTO.isActive())
                .build();
    }
    public PurchasingRequestDTO convertToDTO(PurchasingRequestEntity purchasingRequestEntity){
        return PurchasingRequestDTO.builder()
                .id(purchasingRequestEntity.getId())
                .status(purchasingRequestEntity.getStatus())
                .payment_type(purchasingRequestEntity.getPayment_type())
                .comment(purchasingRequestEntity.getComment())
                .date(purchasingRequestEntity.getDate())
                .vendor(purchasingRequestEntity.getVendor())
                .customer(purchasingRequestEntity.getCustomer())
                .carInstanceEntity(purchasingRequestEntity.getCarInstanceEntity())
                .active(purchasingRequestEntity.isActive())
                .build();
    }
}
