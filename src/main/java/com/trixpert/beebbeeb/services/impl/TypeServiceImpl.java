package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.TypeEntity;
import com.trixpert.beebbeeb.data.mappers.TypeMapper;
import com.trixpert.beebbeeb.data.repositories.TypeRepository;
import com.trixpert.beebbeeb.data.request.TypeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.TypeDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {


    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;
    private final ReporterService reporterService;
    private final UserService userService;
    private final AuditService auditService;
    private final CloudStorageService cloudStorageService;


    public TypeServiceImpl(TypeRepository typeRepository,
                           TypeMapper typeMapper,
                           ReporterService reporterService,
                           UserService userService,
                           AuditService auditService, CloudStorageService cloudStorageService) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
        this.reporterService = reporterService;
        this.userService = userService;
        this.auditService = auditService;
        this.cloudStorageService = cloudStorageService;
    }

    @Override
    public ResponseWrapper<Boolean> addType(
            TypeRegistrationRequest typeRegistrationRequest
            , MultipartFile logoFile, String authHeader) throws IOException {

        String username = auditService.getUsernameForAudit(authHeader);

        String logoUrlRecord = cloudStorageService.uploadFile(logoFile);

        try {
            TypeEntity typeEntityRecord = TypeEntity.builder()
                    .name(typeRegistrationRequest.getName())
                    .description(typeRegistrationRequest.getDescription())
                    .logoUrl(logoUrlRecord)
                    .active(true)
                    .build();
            typeRepository.save(typeEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Adding new type entity " + typeEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("type added successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deleteType(long typeId, String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        Optional<TypeEntity> optionalTypeEntity = typeRepository.findById(typeId);
        try {
            if (!optionalTypeEntity.isPresent())
                throw new NotFoundException("This type doesn't exist");

            TypeEntity typeEntityRecord = optionalTypeEntity.get();
            typeEntityRecord.setActive(false);
            typeRepository.save(typeEntityRecord);


            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("Deleting new type entity " + typeEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Type Deleted Successful ID :".concat(Long.toString(typeId)));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }


    @Override
    public ResponseWrapper<Boolean> updateType(TypeRegistrationRequest typeRegistrationRequest ,
            long typeId ,  String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<TypeEntity> optionalTypeEntity = typeRepository.findById(typeId);

            if (!optionalTypeEntity.isPresent())
                throw new NotFoundException("This type doesn't exist");

            TypeEntity typeEntityRecord = optionalTypeEntity.get();
            if (typeRegistrationRequest.getName() != null &&
                    !typeRegistrationRequest.getName().equals(typeEntityRecord.getName()))
                typeEntityRecord.setName(typeRegistrationRequest.getName());

            typeRepository.save(typeEntityRecord);


            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("Updating new type entity " + typeEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Type updated successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<TypeDTO>> listAllTypes(boolean active) {
        try {
            List<TypeDTO> typeList = new ArrayList<>();
            typeRepository.findAllByActive(active).forEach(type ->
                    typeList.add(typeMapper.convertToDTO(type))
            );

            return reporterService.reportSuccess(typeList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<TypeDTO> getType(long typeId) {
        try{
            Optional<TypeEntity> optionalTypeEntity = typeRepository.findById(typeId);
            if(!optionalTypeEntity.isPresent()){
                throw new NotFoundException("This Type doesn't exist ");
            }
            TypeEntity typeEntityRecord = optionalTypeEntity.get();
            return reporterService.reportSuccess(typeMapper.convertToDTO(typeEntityRecord));

        }catch (Exception e){
            return reporterService.reportError(e);
        }
    }
}
