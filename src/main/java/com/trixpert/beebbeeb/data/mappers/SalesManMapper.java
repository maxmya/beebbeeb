package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.SalesManEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.repositories.VendorRepository;
import com.trixpert.beebbeeb.data.to.SalesManDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Component
public class SalesManMapper {
    private final VendorRepository vendorRepository ;

    public SalesManMapper(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public SalesManEntity convertToEntity(@NotNull SalesManDTO salesManDTO){
        Optional<VendorEntity> vendorEntityOptional = vendorRepository.findById(salesManDTO.getVendorId());
        if(!vendorEntityOptional.isPresent()){
            throw new NotFoundException("Vendor Not Found !");
        }
        return SalesManEntity.builder()
                .id(salesManDTO.getId())
                .active(salesManDTO.isActive())
                .name(salesManDTO.getName())
                .vendor(vendorEntityOptional.get())
                .phone(salesManDTO.getPhone())
                .build();
    }

    public SalesManDTO convertToDTO (@NotNull SalesManEntity salesManEntity){
        return SalesManDTO.builder()
                .id(salesManEntity.getId())
                .active(salesManEntity.isActive())
                .name(salesManEntity.getName())
                .phone(salesManEntity.getPhone())
                .vendorId(salesManEntity.getVendor().getId())
                .build();
    }
}
