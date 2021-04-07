package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.RolesEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.mappers.UserMapper;
import com.trixpert.beebbeeb.data.mappers.VendorMapper;
import com.trixpert.beebbeeb.data.repositories.RolesRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.repositories.VendorRepository;
import com.trixpert.beebbeeb.data.request.VendorRegistrationRequest;
import com.trixpert.beebbeeb.data.request.WokringTimsRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.UserDTO;
import com.trixpert.beebbeeb.data.to.VendorDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {

    private final Logger logger = LoggerFactory.getLogger("VendorService");

    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final RolesRepository rolesRepository;

    private final ReporterService reporterService;
    private final UserService userService;

    private final UserMapper userMapper;
    private final VendorMapper vendorMapper;

    private final AuditService auditService;
    private final CloudStorageService cloudStorageService;


    public VendorServiceImpl(UserRepository userRepository,
                             VendorRepository vendorRepository,
                             RolesRepository rolesRepository,
                             ReporterService reporterService,
                             UserService userService,
                             UserMapper userMapper,
                             VendorMapper vendorMapper, AuditService auditService, CloudStorageService cloudStorageService) {
        this.userRepository = userRepository;
        this.vendorRepository = vendorRepository;
        this.rolesRepository = rolesRepository;
        this.reporterService = reporterService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.vendorMapper = vendorMapper;
        this.auditService = auditService;
        this.cloudStorageService = cloudStorageService;
    }

    @Override
    public ResponseWrapper<Boolean> registerVendor(
            VendorRegistrationRequest vendorRegistrationRequest,
            String authHeader){

        String username = auditService.getUsernameForAudit(authHeader);

        String logoUrlRecord ="";


        if (userRepository.existsByEmail(vendorRegistrationRequest.getEmail())) {
            logger.error("attempt to register existed email ".concat(vendorRegistrationRequest.getEmail()));
            return new ResponseWrapper<>(false,
                    "email already registered", HttpStatus.BAD_REQUEST, false);
        }

        try {

            Optional<RolesEntity> vendorRole = rolesRepository.findByName(Roles.ROLE_VENDOR);

            if (!vendorRole.isPresent()) {
                throw new NotFoundException("Vendor Roles Not Found");
            }

            UserEntity userEntityRecord = userService.registerUser(
                    vendorRegistrationRequest.getEmail(),
                    vendorRole.get(),
                    vendorRegistrationRequest,
                    logoUrlRecord,
                    false
            ).getData();


            VendorEntity vendorEntityRecord = VendorEntity.builder()
                    .name(vendorRegistrationRequest.getVendorName())
                    .manager(userEntityRecord)
                    .mainAddress(vendorRegistrationRequest.getMainAddress())
                    .generalManagerName(vendorRegistrationRequest.getGmName())
                    .generalManagerPhone(vendorRegistrationRequest.getGmPhone())
                    .accountManagerName(vendorRegistrationRequest.getAccManagerName())
                    .accountManagerPhone(vendorRegistrationRequest.getAccManagerPhone())
                    .active(vendorRegistrationRequest.isActive())
                    .build();

            vendorRepository.save(vendorEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Adding new vendor entity " + vendorEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            logger.info("new vendor registered ".concat(vendorRegistrationRequest.getEmail()));
            return reporterService.reportSuccess("vendor registered successfully");

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<VendorDTO>> getAllVendors(boolean active) {
        try {
            //Creating ArrayList for store all vendors on it
            List<VendorDTO> allVendors = new ArrayList<>();
            //Looping On All Vendors on database one by one
            vendorRepository.findAllByActive(active).forEach(vendor -> {
                UserDTO userDTO = userMapper.convertToDTO(vendor.getManager());
                // For Every Vendor Create new Object from vendorDTO and Send needed data
                // From vendorEntity to vendorDTO by passing this data on constructor
                VendorDTO vendorDTO = vendorMapper.convertToDTO(vendor);
                vendorDTO.setManager(userDTO);
                //Append new vendorDTO Object in the list of all vendors
                allVendors.add(vendorDTO);
            });

            return reporterService.reportSuccess(allVendors);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> deleteVendor(long vendorId, String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<VendorEntity> optionalVendorRecord = vendorRepository.findById(vendorId);
            if (!optionalVendorRecord.isPresent()) {
                throw new NotFoundException(" Vendor Entity not found");
            } else {
                VendorEntity vendorRecord = optionalVendorRecord.get();
                vendorRecord.setActive(false);
                vendorRepository.save(vendorRecord);

                AuditDTO auditDTO =
                        AuditDTO.builder()
                                .user(userService.getUserByUsername(username))
                                .action(AuditActions.DELETE)
                                .description("Deleting new vendor entity " + vendorRecord.toString())
                                .timestamp(LocalDateTime.now())
                                .build();

                auditService.logAudit(auditDTO);

                return reporterService.reportSuccess("Vendor soft deleted(deActivated) successfully");
            }
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> updateVendor(VendorRegistrationRequest vendorRegistrationRequest
            ,long vendorId, String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<VendorEntity> optionalVendorRecord = vendorRepository.findById(vendorId);
            if (!optionalVendorRecord.isPresent()) {
                throw new NotFoundException(" Vendor Entity not found");
            }

            VendorEntity vendorRecord = optionalVendorRecord.get();

            if (vendorRegistrationRequest.getVendorName() != null) {
                vendorRecord.setName(vendorRegistrationRequest.getVendorName());
            }

            if (vendorRegistrationRequest.getMainAddress() != null) {
                vendorRecord.setMainAddress(vendorRegistrationRequest.getMainAddress());
            }

            if (vendorRegistrationRequest.getGmName() != null) {
                userService.updateUser(userMapper.convertToDTO(vendorRecord.getManager()));
            }
            if (vendorRegistrationRequest.getGmPhone() != null) {
                vendorRecord.setGeneralManagerPhone(vendorRegistrationRequest.getGmPhone());
            }
            if (vendorRegistrationRequest.getAccManagerName() != null) {
                vendorRecord.setAccountManagerName(vendorRegistrationRequest.getAccManagerName());
            }
            if (vendorRegistrationRequest.getAccManagerPhone() != null) {
                vendorRecord.setAccountManagerPhone(vendorRegistrationRequest.getAccManagerPhone());
            }
            vendorRepository.save(vendorRecord);
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("updating new vendor entity " + vendorRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Vendor updated successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<VendorDTO> getVendor(long vendorId) {
        try {
            Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(vendorId);
            if(!optionalVendorEntity.isPresent()){
                throw new NotFoundException("Vendor doesn't exist");
            }
            VendorEntity vendorEntityRecord = optionalVendorEntity.get();
            return reporterService.reportSuccess(vendorMapper.convertToDTO(vendorEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);

        }
    }

    @Override
    public ResponseWrapper<Boolean> addVendorPhoto(long vendorId, MultipartFile vendorPhoto) {
        try {
            Optional<VendorEntity> vendorEntityOptional = vendorRepository.findById(vendorId);
            if(!vendorEntityOptional.isPresent()){
                throw new NotFoundException("This vendor not exist");
            }
            VendorEntity vendorEntityRecord = vendorEntityOptional.get();
            String photoUrlRecord = cloudStorageService.uploadFile(vendorPhoto);
            Optional<UserEntity> userEntityOptional = userRepository.findById(vendorEntityRecord.getManager().getId());
            if(!userEntityOptional.isPresent()){
                throw new NotFoundException("This User not exist");
            }
            UserEntity userEntityRecord = userEntityOptional.get();
            userEntityRecord.setPicUrl(photoUrlRecord);
            userRepository.save(userEntityRecord);
            vendorRepository.save(vendorEntityRecord);
            return reporterService.reportSuccess("Vendor's Photo Added !");

        }catch (Exception e){
           return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> registerVendorWorkingDays(long vendorId, String wokringTimsRegistrationRequest) {

        try {
            Optional<VendorEntity> vendorEntityOptional = vendorRepository.findById(vendorId);
            if(!vendorEntityOptional.isPresent()){
                throw new NotFoundException("This vendor not exist");
            }
            VendorEntity vendorEntityRecord = vendorEntityOptional.get();
            vendorEntityRecord.setWorkingTime(wokringTimsRegistrationRequest);
            vendorRepository.save(vendorEntityRecord);
            return reporterService.reportSuccess("Vendor's Photo Added !");

        }catch (Exception e){
            return reporterService.reportError(e);
        }
    }
}
