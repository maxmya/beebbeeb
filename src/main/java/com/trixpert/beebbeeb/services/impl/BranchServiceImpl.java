package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.BranchEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.entites.VendorEntity;
import com.trixpert.beebbeeb.data.mappers.BranchMapper;
import com.trixpert.beebbeeb.data.mappers.CarMapper;
import com.trixpert.beebbeeb.data.repositories.BranchRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.repositories.VendorRepository;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.BranchDTO;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {


    private final VendorRepository vendorRepository;
    private final BranchRepository branchRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final VendorService vendorService;
    private final ReporterService reporterService;
    private final AuditService auditService;

    private final BranchMapper branchMapper;
    private final CarMapper carMapper;

    public BranchServiceImpl(VendorRepository vendorRepository,
                             BranchRepository branchRepository,
                             UserRepository userRepository,
                             UserService userService,
                             VendorService vendorService,
                             ReporterService reporterService,
                             AuditService auditService,
                             BranchMapper branchMapper,
                             CarMapper carMapper) {

        this.vendorRepository = vendorRepository;
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.vendorService = vendorService;
        this.reporterService = reporterService;
        this.auditService = auditService;
        this.branchMapper = branchMapper;
        this.carMapper = carMapper;
    }

    @Override
    public ResponseWrapper<Boolean> registerBranchForVendor(BranchDTO branchDTO, Long vendorId ,
                                                            String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(vendorId);
            if (!optionalVendorEntity.isPresent()) {
                throw new NotFoundException("Vendor Not Found");
            }
            VendorEntity vendorEntity = optionalVendorEntity.get();
            BranchEntity branchEntityRecord = branchMapper.convertToEntity(branchDTO);
            branchEntityRecord.setVendor(vendorEntity);
            branchRepository.save(branchEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("adding new branch entity " + branchEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<BranchDTO>> getAllBranchesForVendor(Long vendorId) {
        try {
            List<BranchDTO> branchesList = new ArrayList<>();
            branchRepository.findAllByVendor(vendorRepository.getOne(vendorId)).forEach(branch -> {
                BranchDTO branchDTO = branchMapper.convertToDTO(branch);
                branchesList.add(branchDTO);
            });
            return reporterService.reportSuccess(branchesList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> updateBranchForVendor(BranchDTO branchDTO , String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BranchEntity> optionalBranchRecord = branchRepository.findById(branchDTO.getId());
            if (!optionalBranchRecord.isPresent()) {
                throw new NotFoundException(" Branch Entity not found");
            }
            BranchEntity branchRecord = optionalBranchRecord.get();
            if (branchDTO.getName() != null) {
                branchRecord.setName(branchDTO.getName());
            }
            if (branchDTO.getAddress() != null) {
                branchRecord.setAddress(branchDTO.getAddress());
            }
            if (branchDTO.getBranchManager() != null) {
                Optional<UserEntity> optionalUserRecord = userRepository.findById(branchDTO.getBranchManager().getId());
                if (!optionalUserRecord.isPresent()) {
                    throw new NotFoundException(" User Entity not found");
                }
                userService.updateUser(branchDTO.getBranchManager());
            }
            if (branchDTO.getVendor() != null) {
                vendorService.updateVendor(branchDTO.getVendor() , authHeader);
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


    @Override
    public ResponseWrapper<Boolean> deleteBranchForVendor(Long branchID , String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BranchEntity> optionalBranchRecord = branchRepository.findById(branchID);
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

}
