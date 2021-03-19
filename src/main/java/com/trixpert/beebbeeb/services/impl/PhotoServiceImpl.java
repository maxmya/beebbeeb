package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.PhotoEntity;
import com.trixpert.beebbeeb.data.mappers.PhotoMapper;
import com.trixpert.beebbeeb.data.repositories.PhotoRepository;
import com.trixpert.beebbeeb.data.request.PhotoRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.PhotoDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.PhotoService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

public class PhotoServiceImpl implements PhotoService {
    private final PhotoMapper photoMapper;
    private final PhotoRepository photoRepository;
    private final ReporterService reporterService;
    private final UserService userService;
    private final AuditService auditService;

    public PhotoServiceImpl(PhotoMapper photoMapper, PhotoRepository photoRepository,
                            ReporterService reporterService,
                            UserService userService,
                            AuditService auditService) {
        this.photoMapper = photoMapper;
        this.photoRepository = photoRepository;
        this.reporterService = reporterService;
        this.userService = userService;
        this.auditService = auditService;
    }


    @Override
    public ResponseWrapper<Boolean> registerPhoto(
            PhotoRegistrationRequest photoRegistrationRequest, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);
        try {
            PhotoEntity photoEntityRecord = PhotoEntity.builder()
                    .interior(photoRegistrationRequest.isInterior())
                    .mainPhoto(photoRegistrationRequest.isMainPhoto())
                    .photoUrl(photoRegistrationRequest.getPhotoUrl())
                    .caption(photoRegistrationRequest.getCaption())
                    .description(photoRegistrationRequest.getDescription())
                    .build();
            photoRepository.save(photoEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Adding new photo entity " + photoEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("photo added successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deletePhoto(long photoId, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<PhotoEntity> optionalPhotoEntity = photoRepository.findById(photoId);
            if (!optionalPhotoEntity.isPresent()) {
                throw new NotFoundException("photo Id doesn't exist");
            }
            PhotoEntity photoEntityRecord = optionalPhotoEntity.get();
            photoEntityRecord.setActive(false);
            photoRepository.save(photoEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("Deleting new photo entity " + photoEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("photo added successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> updatePhoto(PhotoRegistrationRequest photoRegistrationRequest,
                                                long photoId, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<PhotoEntity> optionalPhotoEntity = photoRepository.findById(photoId);
            if (!optionalPhotoEntity.isPresent()) {
                throw new NotFoundException("photo Id doesn't exist");
            }
            PhotoEntity photoEntityRecord = optionalPhotoEntity.get();
            if (photoRegistrationRequest.getCaption() != null && !photoRegistrationRequest.getCaption().
                    equals(photoEntityRecord.getCaption())) {
                photoEntityRecord.setCaption(photoRegistrationRequest.getCaption());
            }
            if (photoRegistrationRequest.getDescription() != null && !photoRegistrationRequest.getDescription().
                    equals(photoEntityRecord.getDescription())) {
                photoEntityRecord.setDescription(photoRegistrationRequest.getDescription());

            }
            if (photoRegistrationRequest.getPhotoUrl()!= null && !photoRegistrationRequest.getPhotoUrl().
                    equals(photoEntityRecord.getPhotoUrl())) {
                photoEntityRecord.setPhotoUrl(photoRegistrationRequest.getPhotoUrl());
            }
            if(photoRegistrationRequest.isMainPhoto() != photoEntityRecord.isMainPhoto()){
                photoEntityRecord.setMainPhoto(photoRegistrationRequest.isMainPhoto());
            }
            if(photoRegistrationRequest.isInterior() != photoEntityRecord.isInterior()){
                photoEntityRecord.setInterior(photoRegistrationRequest.isInterior());
            }

            photoRepository.save(photoEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("updating new photo entity " + photoEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("photo added successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<PhotoDTO> getPhoto(long photoId) {

        try {
            Optional<PhotoEntity> optionalPhotoEntity = photoRepository.findById(photoId);
            if (!optionalPhotoEntity.isPresent()) {
                throw new NotFoundException("photo Id doesn't exist");
            }
            PhotoEntity photoEntityRecord = optionalPhotoEntity.get();

            return reporterService.reportSuccess(photoMapper.convertToDTO(photoEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }    }
}
