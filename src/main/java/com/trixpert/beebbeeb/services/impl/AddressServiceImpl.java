package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.*;
import com.trixpert.beebbeeb.data.mappers.AddressMapper;
import com.trixpert.beebbeeb.data.repositories.AddressRepository;
import com.trixpert.beebbeeb.data.repositories.CustomerRepository;
import com.trixpert.beebbeeb.data.request.AddressRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AddressDTO;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AddressService;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private final AuditService auditService;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    private final ReporterService reporterService;
    private final UserService userService;
    private final CustomerRepository customerRepository;


    public AddressServiceImpl(AuditService auditService, AddressMapper addressMapper,
                              AddressRepository addressRepository,
                              ReporterService reporterService, UserService userService,
                              CustomerRepository customerRepository) {
        this.auditService = auditService;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
        this.reporterService = reporterService;
        this.userService = userService;
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseWrapper<Boolean> addAddress(AddressRegistrationRequest addressRegistrationRequest,
                                               String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(addressRegistrationRequest.getCustomerId());
            if (!optionalCustomerEntity.isPresent()) {
                throw new NotFoundException("This Customer doesn't not Exits !");
            }
            AddressEntity addressEntityRecord = AddressEntity.builder()
                    .customer(optionalCustomerEntity.get())
                    .longitude(addressRegistrationRequest.getLongitude())
                    .latitude(addressRegistrationRequest.getLatitude())
                    .fullAddress(addressRegistrationRequest.getFullAddress())
                    .title(addressRegistrationRequest.getTitle())
                    .active(true)
                    .build();
            addressRepository.save(addressEntityRecord);
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("adding new Address entity " + addressRepository.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);
            return reporterService.reportSuccess("Address Registered Successfully");

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }


    @Override
    public ResponseWrapper<List<AddressDTO>> listAllAddresses(boolean active) {
        try {
            List<AddressDTO> addressList = new ArrayList<>();
            addressRepository.findAllByActive(active).forEach(address ->
                    addressList.add(addressMapper.convertToDTO(address)));
            return reporterService.reportSuccess(addressList);
        } catch (Exception e) {
            return  reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> updateAddress(AddressRegistrationRequest addressRegistrationRequest
            , Long addressId, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<AddressEntity> optionalAddressEntity = addressRepository.findById(addressId);
            if (!optionalAddressEntity.isPresent()) {
                throw new NotFoundException("This Address does not Exits !");
            }
            AddressEntity addressEntityRecord = optionalAddressEntity.get();

            if(addressRegistrationRequest.getTitle() != null && addressRegistrationRequest.getTitle().equals(
                    addressEntityRecord.getTitle())){
                addressEntityRecord.setTitle(addressRegistrationRequest.getTitle());
            }
            if(addressRegistrationRequest.getFullAddress() != null && addressRegistrationRequest.getFullAddress().equals(
                    addressEntityRecord.getFullAddress())){
                addressEntityRecord.setFullAddress(addressRegistrationRequest.getFullAddress());
            }
            if(addressRegistrationRequest.getLatitude() != -1 &&
            addressRegistrationRequest.getLatitude() != addressEntityRecord.getLatitude()){
             addressEntityRecord.setLatitude(addressRegistrationRequest.getLatitude());
            }
            if(addressRegistrationRequest.getLongitude() != -1 &&
            addressRegistrationRequest.getLongitude() != addressEntityRecord.getLongitude()){
                addressEntityRecord.setLongitude(addressRegistrationRequest.getLongitude());
            }
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("Updating new Address " + addressId)
                            .timestamp(LocalDateTime.now())
                            .build();
            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("A new customer  has been added ");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deleteAddress(Long addressId, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<AddressEntity> optionalAddressEntity = addressRepository.findById(addressId);
            if (!optionalAddressEntity.isPresent()) {
                throw new NotFoundException("This Address does not Exits !");
            }
            AddressEntity addressEntityRecord = optionalAddressEntity.get();
            addressEntityRecord.setActive(false);

            addressRepository.save(addressEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("Deleting new Address entity " + addressEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Model Deleted Success !");

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<AddressDTO> getAddress(long addressId) {
        try {
            Optional<AddressEntity> optionalAddressEntity = addressRepository.findById(addressId);

            if (!optionalAddressEntity.isPresent()) {
                throw new NotFoundException("Address Not Found");
            }
            AddressEntity addressEntityRecord = optionalAddressEntity.get();
            return reporterService.reportSuccess(addressMapper.convertToDTO(addressEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
