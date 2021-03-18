package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.ParentColorEntity;
import com.trixpert.beebbeeb.data.mappers.ParentColorMapper;
import com.trixpert.beebbeeb.data.repositories.ParentColorRepository;
import com.trixpert.beebbeeb.data.request.ParentColorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.ParentColorDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.ParentColorService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParentColorServiceImpl implements ParentColorService {
    final private ParentColorRepository  parentColorRepository ;
    final private ParentColorMapper parentColorMapper;
    private final ReporterService reporterService;
    private final UserService userService;
    private final AuditService auditService;


    public ParentColorServiceImpl(ParentColorRepository parentColorRepository,
                                  ParentColorMapper parentColorMapper,
                                  ReporterService reporterService,
                                  UserService userService,
                                  AuditService auditService){
        this.parentColorRepository = parentColorRepository ;
        this.parentColorMapper = parentColorMapper ;
        this.reporterService = reporterService ;
        this.userService = userService;
        this.auditService = auditService;
    }
    @Override
    public ResponseWrapper<Boolean> AddParentColor(
            ParentColorRegistrationRequest parentColorRegistrationRequest
    , String authHeader){

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            ParentColorEntity parentColorEntityRecord = ParentColorEntity.builder()
                    .name(parentColorRegistrationRequest.getName())
                    .active(true)
                    .build();
            parentColorRepository.save(parentColorEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Adding new Parent Color entity " +
                                    parentColorEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("parent color added successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }
    @Override
    public ResponseWrapper<Boolean> deleteParentColor(long parentColorId , String authHeader){

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<ParentColorEntity> optionalParentColorEntity = parentColorRepository
                    .findById(parentColorId);
            if (!optionalParentColorEntity.isPresent()){
                throw new NotFoundException("This Parent Color does not exist");
            }
            ParentColorEntity parentColorEntityRecord = optionalParentColorEntity.get();
            parentColorEntityRecord.setActive(false);
            parentColorRepository.save(parentColorEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("Deleting new Parent Color entity " +
                                    parentColorEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Parent Color Deleted Successful ID :"
                    .concat(Long.toString(parentColorId)));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
        }

    @Override
    public ResponseWrapper<Boolean> updateParentColor(ParentColorRegistrationRequest parentColorRegistrationRequest
           , long parentColorId , String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<ParentColorEntity> optionalParentColorEntity = parentColorRepository
                    .findById(parentColorId);
            if (!optionalParentColorEntity.isPresent()) {
                throw new NotFoundException("This ParentColor Was Not Found");
            }
            ParentColorEntity parentColorEntityRecord = optionalParentColorEntity.get();
            if (parentColorRegistrationRequest.getName() != null &&
                    !parentColorRegistrationRequest.getName().equals(
                    parentColorEntityRecord.getName())) {
                parentColorEntityRecord.setName(parentColorRegistrationRequest.getName());
            }
            parentColorRepository.save(parentColorEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("updating new Parent Color entity " + parentColorEntityRecord.
                                    toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);

        }
    }
    @Override
    public ResponseWrapper<List<ParentColorDTO>> getAllParentColors(boolean active) {
        try {
            List<ParentColorDTO> parentcolorList = new ArrayList<>();
            parentColorRepository.findAllByActive(active).forEach(parentcolor ->
                    parentcolorList.add(parentColorMapper.convertToDTO(parentcolor))
            );
            return reporterService.reportSuccess(parentcolorList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

        }

    @Override
    public ResponseWrapper<ParentColorDTO> getParentColor(long parentColorId) {
        try{
            Optional<ParentColorEntity> optionalParentColorEntity = parentColorRepository.findById(
                    parentColorId);
            if(!optionalParentColorEntity.isPresent()){
                throw new NotFoundException("This Parent color doesn't exist");
            }
            ParentColorEntity parentColorEntityRecord = optionalParentColorEntity.get();
            return reporterService.reportSuccess(parentColorMapper.convertToDTO(parentColorEntityRecord));
        }
        catch (Exception e){
            return reporterService.reportError(e);
        }
    }
}


