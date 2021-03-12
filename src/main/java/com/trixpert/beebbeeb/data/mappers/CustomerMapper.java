package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.to.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    private final UserMapper userMapper ;

    public CustomerMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public CustomerEntity convertToEntity(CustomerDTO customerDTO){
        return CustomerEntity.builder()
                .id(customerDTO.getId())
                .user(userMapper.convertToEntity(customerDTO.getUser()))
                .income(customerDTO.getIncome())
                .jobAddress(customerDTO.getJobAddress())
                .jobTitle(customerDTO.getJobTitle())
                .preferredBank(customerDTO.getPreferredBank())
                .active(customerDTO.isActive())
                .build();

    }
    public CustomerDTO convertToDTO(CustomerEntity customerEntity){
        return CustomerDTO.builder()
                .id(customerEntity.getId())
                .income(customerEntity.getIncome())
                .jobAddress(customerEntity.getJobAddress())
                .jobTitle(customerEntity.getJobTitle())
                .preferredBank(customerEntity.getPreferredBank())
                .active(customerEntity.isActive())
                .user(userMapper.convertToDTO(customerEntity.getUser()))
                .build();
    }
}
