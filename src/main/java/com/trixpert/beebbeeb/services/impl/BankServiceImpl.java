package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.*;
import com.trixpert.beebbeeb.data.mappers.BankMapper;
import com.trixpert.beebbeeb.data.repositories.*;
import com.trixpert.beebbeeb.data.request.BankRegistrationRequest;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.BankDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final VendorRepository vendorRepository;
    private final CustomerRepository customerRepository;

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
                           VendorRepository vendorRepository, CustomerRepository customerRepository, AuditService auditService,
                           BankMapper bankMapper) {

        this.bankRepository = bankRepository;
        this.cloudStorageService = cloudStorageService;
        this.reporterService = reporterService;
        this.userService = userService;
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
        this.vendorRepository = vendorRepository;
        this.customerRepository = customerRepository;
        this.auditService = auditService;
        this.bankMapper = bankMapper;
    }

    @Override
    public ResponseWrapper<Boolean> registerBank(MultipartFile logoFile,
                                                 BankRegistrationRequest bankRegistrationRequest,
                                                 String authHeader) throws IOException {


        String username = auditService.getUsernameForAudit(authHeader);

        String logoUrlRecord = cloudStorageService.uploadFile(logoFile);

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
                    bankRole.get(), registrationRequest , "", false).getData();

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

    @Transactional
    @Override
    public ResponseWrapper<Boolean> updateBank(
            MultipartFile logoFile, BankRegistrationRequest bankRegistrationRequest , long bankId ,
            String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BankEntity> optionalBankRecord = bankRepository.findById(bankId);
            if (!optionalBankRecord.isPresent()) {
                throw new NotFoundException("Bank entity not found");
            }
            BankEntity bankEntityRecord = optionalBankRecord.get();
            if (bankRegistrationRequest.getName() != null) {
                bankEntityRecord.setName(bankRegistrationRequest.getName());
            }
            if (logoFile != null) {
                String logoUrlRecord = cloudStorageService.uploadFile(logoFile);
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

    @Transactional
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

    @Override
    public ResponseWrapper<BankDTO> getBank(long bankId) {
        try {
            Optional<BankEntity> optionalBankEntity = bankRepository.findById(bankId);
            if (!optionalBankEntity.isPresent()) {
                throw new NotFoundException("This bank doesn't exist");
            }
            BankEntity bankEntityRecord = optionalBankEntity.get();
            return reporterService.reportSuccess(bankMapper.convertToDTO(bankEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<BankDTO>> getBanksForVendor(long vendorId) {
        try {
            Optional<VendorEntity> optionalVendorEntity = vendorRepository.findById(vendorId);
            if(!optionalVendorEntity.isPresent()){
                throw new NotFoundException("This Vendor doesn't exist");
            }
            List<BankEntity> banksList = optionalVendorEntity.get().getBanks();
            List<BankDTO> bankDTOS = new ArrayList<>();
            for(BankEntity bank : banksList){
                bankDTOS.add(bankMapper.convertToDTO(bank));
            }
            return reporterService.reportSuccess(bankDTOS);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
    @Override
    public ResponseWrapper<List<BankDTO>> getBanksForCustomer(long customerId) {
        try {
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);
            if(!optionalCustomerEntity.isPresent()){
                throw new NotFoundException("This Customer doesn't exist");
            }
            List<BankEntity> banksList = optionalCustomerEntity.get().getBanks();
            List<BankDTO> bankDTOS = new ArrayList<>();
            for(BankEntity bank : banksList){
                bankDTOS.add(bankMapper.convertToDTO(bank));
            }
            return reporterService.reportSuccess(bankDTOS);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

}
