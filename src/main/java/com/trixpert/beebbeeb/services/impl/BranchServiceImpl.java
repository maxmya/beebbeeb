package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.BranchEntity;
import com.trixpert.beebbeeb.data.entites.RolesEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.mappers.BranchMapper;
import com.trixpert.beebbeeb.data.mappers.CarMapper;
import com.trixpert.beebbeeb.data.repositories.BranchRepository;
import com.trixpert.beebbeeb.data.repositories.RolesRepository;
import com.trixpert.beebbeeb.data.repositories.VendorRepository;
import com.trixpert.beebbeeb.data.request.BranchRegistrationRequest;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.BranchDTO;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.UserDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {


    private final VendorRepository vendorRepository;
    private final BranchRepository branchRepository;
    private final RolesRepository rolesRepository;

    private final UserService userService;
    private final ReporterService reporterService;
    private final AuditService auditService;

    private final BranchMapper branchMapper;
    private final CarMapper carMapper;

    public BranchServiceImpl(VendorRepository vendorRepository,
                             BranchRepository branchRepository,
                             RolesRepository rolesRepository,
                             UserService userService,
                             ReporterService reporterService,
                             AuditService auditService,
                             BranchMapper branchMapper,
                             CarMapper carMapper) {

        this.vendorRepository = vendorRepository;
        this.branchRepository = branchRepository;
        this.rolesRepository = rolesRepository;
        this.userService = userService;
        this.reporterService = reporterService;
        this.auditService = auditService;
        this.branchMapper = branchMapper;
        this.carMapper = carMapper;
    }

    @Override
    public ResponseWrapper<Boolean> registerBranchForVendor(
            BranchRegistrationRequest branchRegistrationRequest, long vendorId ,
            String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<RolesEntity> branchRole = rolesRepository.findByName(Roles.ROLE_BRANCH);
            RegistrationRequest registrationRequest = new RegistrationRequest(
                    branchRegistrationRequest.getName(),
                    branchRegistrationRequest.getPhone(),
                    branchRegistrationRequest.getEmail(),
                    branchRegistrationRequest.isActive(),
                    branchRegistrationRequest.getPassword()
            );
            UserEntity userEntityRecord = userService.registerUser(
                    branchRegistrationRequest.getEmail(),
                    branchRole.get(),
                    registrationRequest,
                    false
            ).getData();
            Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(vendorId);
            if (!optionalVendorEntity.isPresent()) {
                throw new NotFoundException("Vendor Not Found");
            }
            VendorEntity vendorEntityRecord = optionalVendorEntity.get();

            BranchEntity branchEntity = BranchEntity.builder()
                    .name(branchRegistrationRequest.getBranchName())
                    .address(branchRegistrationRequest.getAddress())
                    .user(userEntityRecord)
                    .vendor(vendorEntityRecord)
                    .active(branchRegistrationRequest.isActive())
                    .build();
            branchRepository.save(branchEntity);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("adding new branch entity " + branchEntity.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<BranchDTO>> getAllBranchesForVendor(long vendorId, boolean active) {
        try {
            List<BranchDTO> branchesList = new ArrayList<>();
            branchRepository.findAllByVendorAndActive(vendorRepository.getOne(vendorId), active).forEach(branch -> {
                BranchDTO branchDTO = branchMapper.convertToDTO(branch);
                branchesList.add(branchDTO);
            });
            return reporterService.reportSuccess(branchesList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
    @Transactional
    @Override
    public ResponseWrapper<Boolean> updateBranchForVendor(BranchRegistrationRequest branchRegistrationRequest,
                                                          long branchId,
                                                          String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BranchEntity> optionalBranchRecord = branchRepository.findById(branchId);
            if (!optionalBranchRecord.isPresent()) {
                throw new NotFoundException(" Branch Entity not found");
            }
            BranchEntity branchRecord = optionalBranchRecord.get();
            if (branchRegistrationRequest.getName() != null) {
                branchRecord.setName(branchRegistrationRequest.getName());
            }
            if (branchRegistrationRequest.getAddress() != null) {
                branchRecord.setAddress(branchRegistrationRequest.getAddress());
            }
            if (branchRegistrationRequest.getName() != null ||
                    branchRegistrationRequest.getEmail() != null
                    || branchRegistrationRequest.getPhone() != null) {
                UserDTO branchManagerDTO = UserDTO.builder()
                        .id(branchRecord.getUser().getId())
                        .name(branchRegistrationRequest.getName())
                        .email(branchRegistrationRequest.getEmail())
                        .phone(branchRegistrationRequest.getPhone())
                        .build();
                userService.updateUser(branchManagerDTO);
            }
            if (branchRegistrationRequest.getVendorId() != -1 &&
                    branchRegistrationRequest.getVendorId()!=branchRecord.getVendor().getId()) {
                Optional<VendorEntity> optionalVendorRecord = vendorRepository.
                        findById(branchRegistrationRequest.getVendorId());
                if (!optionalVendorRecord.isPresent()) {
                    throw new NotFoundException(" Vendor Entity not found");
                }
                branchRecord.setVendor(optionalVendorRecord.get());
            }
            branchRepository.save(branchRecord);


            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("updating new branch entity " + branchRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Branch updated successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> deleteBranchForVendor(long branchId  , String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BranchEntity> optionalBranchRecord = branchRepository.findById(branchId);
            if (!optionalBranchRecord.isPresent()) {
                throw new NotFoundException(" Branch Entity not found");
            } else {
                BranchEntity branchRecord = optionalBranchRecord.get();
                branchRecord.setActive(false);
                branchRepository.save(branchRecord);


                AuditDTO auditDTO =
                        AuditDTO.builder()
                                .user(userService.getUserByUsername(username))
                                .action(AuditActions.DELETE)
                                .description("Deleting new branch entity " + branchRecord.toString())
                                .timestamp(LocalDateTime.now())
                                .build();

                auditService.logAudit(auditDTO);
                return reporterService.reportSuccess("Branch soft deleted(deActivated) successfully");
            }
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<CarDTO>> listCarsForBranch(long branchId){
        try{
            List<CarDTO> listCars = new ArrayList<>();
            Optional<BranchEntity> optionalBranchEntity = branchRepository.findById(branchId);
            if(!optionalBranchEntity.isPresent()){
                throw new NotFoundException("Branch entity not found");
            }
            optionalBranchEntity.get().getCars().forEach(car ->
                    listCars.add(carMapper.convertToDTO(car))
            );
            return reporterService.reportSuccess(listCars);
        }
        catch(Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<BranchDTO> getBranchForVendor(long vendorId, long branchId) {
        try{
            Optional<VendorEntity> optionalVendorRecord = vendorRepository.findById(vendorId);
            if (!optionalVendorRecord.isPresent()) {
                throw new NotFoundException(" Vendor Entity not found");
            }
            Optional<BranchEntity> optionalBranchRecord = branchRepository.findById(branchId);
            if (!optionalBranchRecord.isPresent()) {
                throw new NotFoundException(" Branch Entity not found");
            }

            BranchEntity branchEntityrecord = optionalBranchRecord.get();

                return reporterService.reportSuccess(branchMapper.convertToDTO(branchEntityrecord));
        }catch (Exception e ){
            return reporterService.reportError(e);
        }
    }

}
