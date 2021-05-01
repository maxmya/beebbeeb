package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.HomeTelephoneEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.repositories.VendorRepository;
import com.trixpert.beebbeeb.data.to.HomeTelephoneDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Component
public class HomeTelephoneMapper {
    private final VendorRepository vendorRepository   ;

    public HomeTelephoneMapper(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public HomeTelephoneEntity convertToEntity(@NotNull HomeTelephoneDTO homeTelephoneDTO){
       Optional<VendorEntity> vendorEntity = vendorRepository.findById(homeTelephoneDTO.getVendorId());
       if (!vendorEntity.isPresent()){
           throw new NotFoundException("Vendor Not Found");
       }
        return HomeTelephoneEntity.builder()
                .id(homeTelephoneDTO.getId())
                .telephoneNumber(homeTelephoneDTO.getTelephoneNumber())
                .vendor(vendorEntity.get())
                .build();

    }

    public HomeTelephoneDTO convertToDTO(@NotNull HomeTelephoneEntity homeTelephoneEntity){
        return HomeTelephoneDTO.builder()
                .id(homeTelephoneEntity.getId())
                .telephoneNumber(homeTelephoneEntity.getTelephoneNumber())
                .vendorId(homeTelephoneEntity.getVendor().getId())
                .active(homeTelephoneEntity.isActive())
                .build();
    }
}
