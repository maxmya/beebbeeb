package com.trixpert.beebbeeb.data.mappers;


import com.trixpert.beebbeeb.data.entites.PriceEntity;
import com.trixpert.beebbeeb.data.to.PriceDTO;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {

    public PriceEntity convertToEntity(PriceDTO priceDTO) {
        return PriceEntity.builder()
                .id(priceDTO.getId())
                .date(priceDTO.getDate())
                .amount(priceDTO.getAmount())
                .active(priceDTO.isActive())
                .build();
    }

    public PriceDTO convertToDTO(PriceEntity priceEntity) {
        return PriceDTO.builder()
                .id(priceEntity.getId())
                .date(priceEntity.getDate())
                .amount(priceEntity.getAmount())
                .active(priceEntity.isActive())
                .build();
    }
}
