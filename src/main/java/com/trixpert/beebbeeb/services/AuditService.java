package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.to.AuditDTO;

import java.util.List;

public interface AuditService {

    String getUsernameForAudit(String authHeader);

    void logAudit(AuditDTO auditDTO);

    List<AuditDTO> getLast200Audits();

    List<AuditDTO> getAuditsByAction(String action);

}
