package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.AuditEntity;
import com.trixpert.beebbeeb.data.mappers.AuditMapper;
import com.trixpert.beebbeeb.data.repositories.AuditRepository;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.security.jwt.JwtProvider;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.ReporterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;
    private final ReporterService reporterService;
    private final JwtProvider jwtProvider;

    public AuditServiceImpl(AuditRepository auditRepository,
                            AuditMapper auditMapper,
                            ReporterService reporterService,
                            JwtProvider jwtProvider) {

        this.auditRepository = auditRepository;
        this.auditMapper = auditMapper;
        this.reporterService = reporterService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String getUsernameForAudit(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");
            return jwtProvider.getUsernameFromToken(token);
        } else throw new IllegalArgumentException("Wrong Auth Header");
    }

    @Override
    public void logAudit(AuditDTO auditDTO) {
        try {
            auditRepository.save(auditMapper.convertToEntity(auditDTO));
        } catch (Exception e) {
            reporterService.reportError(e);
        }
    }

    @Override
    public List<AuditDTO> getLast200Audits() {
        try {
            List<AuditEntity> auditEntityList = auditRepository.findAll();
            List<AuditDTO> auditDTOList = new ArrayList<>();
            auditEntityList.forEach(auditEntity -> auditDTOList.add(auditMapper.convertToDTO(auditEntity)));
            return auditDTOList;
        } catch (Exception e) {
            reporterService.reportError(e);
            return null;
        }
    }

    @Override
    public List<AuditDTO> getAuditsByAction(String action) {
        try {
            List<AuditEntity> auditEntityList = auditRepository.findAllByAction(action);
            List<AuditDTO> auditDTOList = new ArrayList<>();
            auditEntityList.forEach(auditEntity -> auditDTOList.add(auditMapper.convertToDTO(auditEntity)));
            return auditDTOList;
        } catch (Exception e) {
            reporterService.reportError(e);
            return null;
        }
    }


}
