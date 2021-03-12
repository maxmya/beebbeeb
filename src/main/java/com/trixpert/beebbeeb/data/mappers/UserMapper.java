package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.to.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity convertToEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .active(userDTO.isActive())
                .picUrl(userDTO.getPictureUrl())
                .build();
    }

    public UserDTO convertToDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .active(userEntity.isActive())
                .pictureUrl(userEntity.getPicUrl())
                .build();
    }


}
