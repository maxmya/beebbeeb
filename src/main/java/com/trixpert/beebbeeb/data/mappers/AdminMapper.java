package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.AdminEntity;
import com.trixpert.beebbeeb.data.to.AdminDTO;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    private final UserMapper userMapper;

    public AdminMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public AdminEntity convertToEntity(AdminDTO adminDTO){
        return AdminEntity.builder()
                .id(adminDTO.getId())
                .title(adminDTO.getTitle())
                .pending(adminDTO.isPending())
                .user(userMapper.convertToEntity(adminDTO.getUser()))
                .active(adminDTO.isActive())
                .build();
    }

    public AdminDTO convertToDTO(AdminEntity adminEntity){
        return AdminDTO.builder()
                .id(adminEntity.getId())
                .title(adminEntity.getTitle())
                .pending(adminEntity.isPending())
                .user(userMapper.convertToDTO(adminEntity.getUser()))
                .active(adminEntity.isActive())
                .build();
    }
}
