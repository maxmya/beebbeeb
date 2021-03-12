package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.BankEntity;
import com.trixpert.beebbeeb.data.entites.RolesEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.mappers.BankMapper;
import com.trixpert.beebbeeb.data.repositories.BankRepository;
import com.trixpert.beebbeeb.data.repositories.RolesRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.request.BankRegistrationRequest;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.BankDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;

    private final CloudStorageService cloudStorageService;
    private final ReporterService reporterService;
    private final UserService userService;
    private final AuditService auditService;

    private final BankMapper bankMapper;


    public BankServiceImpl(BankRepository bankRepository,
                           CloudStorageService cloudStorageService,
                           ReporterService reporterService,
                           UserService userService,
                           RolesRepository rolesRepository,
                           UserRepository userRepository,
                           AuditService auditService,
                           BankMapper bankMapper) {

        this.bankRepository = bankRepository;
        this.cloudStorageService = cloudStorageService;
        this.reporterService = reporterService;
        this.userService = userService;
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
        this.auditService = auditService;
        this.bankMapper = bankMapper;
    }

    @Override
    public ResponseWrapper<Boolean> registerBank(MultipartFile logoFile,
                                                 BankRegistrationRequest bankRegistrationRequest,
                                                 String authHeader) throws IOException {


        String username = auditService.getUsernameForAudit(authHeader);

        File convFile = new File(logoFile.getName());
        logoFile.transferTo(convFile);

        String logoUrlRecord = cloudStorageService
                .uploadFile(convFile.getPath(), convFile.getName(), convFile.getAbsoluteFile());
        try {
            RegistrationRequest registrationRequest = new RegistrationRequest(
                    bankRegistrationRequest.getName(),
                    bankRegistrationRequest.getPhone(),
                    bankRegistrationRequest.getEmail(),
                    bankRegistrationRequest.isActive(),
                    bankRegistrationRequest.getPassword()
            );

            Optional<RolesEntity> bankRole = rolesRepository.findByName(Roles.ROLE_BANK);

            if (!bankRole.isPresent()) {
                throw new NotFoundException("Bank Role Not Found !");
            }

            UserEntity userEntity = userService.registerUser(bankRegistrationRequest.getEmail(),
                    bankRole.get(), registrationRequest, false).getData();

            BankEntity bankEntityRecord = BankEntity.builder()
                    .name(bankRegistrationRequest.getBankName())
                    .user(userEntity)
                    .logoUrl(logoUrlRecord)
                    .active(bankRegistrationRequest.isActive())
                    .build();
            bankRepository.save(bankEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("adding new bank entity " + bankEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("A new Bank has been registered successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> updateBank(MultipartFile logoFile, BankDTO bankDTO, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BankEntity> optionalBankRecord = bankRepository.findById(bankDTO.getId());
            if (!optionalBankRecord.isPresent()) {
                throw new NotFoundException("Bank entity not found");
            }
            BankEntity bankEntityRecord = optionalBankRecord.get();
            if (bankDTO.getName() != null) {
                bankEntityRecord.setName(bankDTO.getName());
            }
            if (bankDTO.getUser() != null) {
                Optional<UserEntity> optionalUserRecord = userRepository.findById(bankDTO.getUser().getId());
                if (!optionalUserRecord.isPresent()) {
                    throw new NotFoundException(" User Entity not found");
                }
                userService.updateUser(bankDTO.getUser());
            }
            if (bankDTO.getLogoUrl() != null) {
                File convFile = new File(logoFile.getName());
                logoFile.transferTo(convFile);
                String logoUrlRecord = cloudStorageService.uploadFile(convFile.getPath(), convFile.getName(), convFile.getAbsoluteFile());
                bankEntityRecord.setLogoUrl(logoUrlRecord);
            }
            bankRepository.save(bankEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("updating new bank entity " + bankEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Bank was updated successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deleteBank(Long bankId, String authHeader) {
        try {
            String username = auditService.getUsernameForAudit(authHeader);

            Optional<BankEntity> optionalBankRecord = bankRepository.findById(bankId);
            if (!optionalBankRecord.isPresent()) {
                throw new NotFoundException("Bank entity not found");
            }
            BankEntity bankEntityRecord = optionalBankRecord.get();
            bankEntityRecord.setActive(false);
            bankRepository.save(bankEntityRecord);
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("deleting new bank entity " + bankEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);
            return reporterService.reportSuccess("Bank with the id: ".concat(Long.toString(bankId))
                    .concat(" was deleted successfully"));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<BankDTO>> getAllBanks(boolean active) {

        try {
            List<BankDTO> banksList = new ArrayList<>();
            bankRepository.findAllByActive(active).forEach(bank ->
                    banksList.add(bankMapper.convertToDTO(bank))
            );
            return reporterService.reportSuccess(banksList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
