package com.trixpert.beebbeeb.data.mappers;

import com.trixpert.beebbeeb.data.entites.AuditEntity;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import org.springframework.stereotype.Component;

@Component
public class AuditMapper {

    private final UserMapper userMapper;

    public AuditMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public AuditEntity convertToEntity(AuditDTO auditDTO) {
        return AuditEntity.builder()
                .id(auditDTO.getId())
                .action(auditDTO.getAction())
                .clientId(auditDTO.getClientId())
                .description(auditDTO.getDescription())
                .latitude(auditDTO.getLatitude())
                .longitude(auditDTO.getLongitude())
                .timestamp(auditDTO.getTimestamp())
                .title(auditDTO.getTitle())
                .trace(auditDTO.getTrace())
                .user(userMapper.convertToEntity(auditDTO.getUser()))
                .build();
    }

    public AuditDTO convertToDTO(AuditEntity auditEntity) {
        return AuditDTO.builder()
                .id(auditEntity.getId())
                .action(auditEntity.getAction())
                .clientId(auditEntity.getClientId())
                .description(auditEntity.getDescription())
                .latitude(auditEntity.getLatitude())
                .longitude(auditEntity.getLongitude())
                .timestamp(auditEntity.getTimestamp())
                .title(auditEntity.getTitle())
                .trace(auditEntity.getTrace())
                .user(userMapper.convertToDTO(auditEntity.getUser()))
                .build();
    }


}
