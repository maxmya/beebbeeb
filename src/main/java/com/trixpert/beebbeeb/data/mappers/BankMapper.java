package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.BankEntity;
import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.to.BankDTO;
import com.trixpert.beebbeeb.data.to.CustomerDTO;
import com.trixpert.beebbeeb.data.to.VendorDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BankMapper {

    private final UserMapper userMapper;

    public BankMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public BankEntity convertToEntity(BankDTO bankDTO){

        return BankEntity.builder()
                .id(bankDTO.getId())
                .name(bankDTO.getName())
                .user(userMapper.convertToEntity(bankDTO.getUser()))
                .logoUrl(bankDTO.getLogoUrl())
                .active(bankDTO.isActive())
                .build();
    }

    public BankDTO convertToDTO(BankEntity bankEntity){
        return BankDTO.builder()
                .id(bankEntity.getId())
                .name(bankEntity.getName())
                .user(userMapper.convertToDTO(bankEntity.getUser()))
                .logoUrl(bankEntity.getLogoUrl())
                .active(bankEntity.isActive())
                .build();
    }
}
