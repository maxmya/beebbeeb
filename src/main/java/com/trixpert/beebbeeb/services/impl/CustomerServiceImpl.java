package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.mappers.AddressMapper;
import com.trixpert.beebbeeb.data.repositories.CustomerRepository;
import com.trixpert.beebbeeb.data.request.CustomerRegistrationRequest;
import com.trixpert.beebbeeb.data.response.CustomerResponse;
import com.trixpert.beebbeeb.data.response.LinkableImage;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AddressDTO;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.UserDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.CustomerService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final UserService userService;
    private final CustomerRepository customerRepository;

    private final ReporterService reporterService;
    private final AuditService auditService;

    private final AddressMapper addressMapper;


    public CustomerServiceImpl(UserService userService,
                               CustomerRepository customerRepository,
                               ReporterService reporterService,
                               AuditService auditService, AddressMapper addressMapper) {

        this.userService = userService;
        this.customerRepository = customerRepository;
        this.reporterService = reporterService;
        this.auditService = auditService;
        this.addressMapper = addressMapper;
    }


    @Transactional
    @Override
    public ResponseWrapper<Boolean> deleteCustomer(long customerId, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);
        try {

            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);

            if (!optionalCustomerEntity.isPresent()) {
                throw new NotFoundException("customer Not Found");
            }
            CustomerEntity customerEntityRecord = optionalCustomerEntity.get();
            customerEntityRecord.setActive(false);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("Deleting new customer " + customerId)
                            .timestamp(LocalDateTime.now())
                            .build();
            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("A new customer  has been added ");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> updateCustomer(CustomerRegistrationRequest customerRegistrationRequest,
                                                   long customerId, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);
        try {

            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(
                    customerId);

            if (!optionalCustomerEntity.isPresent()) {
                throw new NotFoundException("customer Not Found");
            }
            CustomerEntity customerEntityRecord = optionalCustomerEntity.get();

            if (customerRegistrationRequest.getIncome() != -1 &&
                    customerRegistrationRequest.getIncome() != customerEntityRecord.getIncome()) {
                customerEntityRecord.setIncome(customerRegistrationRequest.getIncome());
            }
            if (customerRegistrationRequest.getJobAddress() != null &&
                    !customerRegistrationRequest.getJobAddress()
                            .equals(customerEntityRecord.getJobAddress())) {
                customerEntityRecord.setJobAddress(customerRegistrationRequest.getJobAddress());
            }
            if (customerRegistrationRequest.getJobTitle() != null &&
                    !customerRegistrationRequest.getJobTitle().equals(
                            customerEntityRecord.getJobTitle())) {
                customerEntityRecord.setJobTitle(customerRegistrationRequest.getJobTitle());
            }
            if (customerRegistrationRequest.getPreferredBank() != null &&
                    !customerRegistrationRequest.getPreferredBank().equals(
                            customerEntityRecord.getPreferredBank())) {
                customerEntityRecord.setPreferredBank(customerRegistrationRequest.getPreferredBank());
            }
            if (customerRegistrationRequest.getName() != null ||
                    customerRegistrationRequest.getEmail() != null
                    || customerRegistrationRequest.getPhone() != null) {
                UserDTO employeeDTO = UserDTO.builder()
                        .id(customerEntityRecord.getUser().getId())
                        .name(customerRegistrationRequest.getName())
                        .email(customerRegistrationRequest.getEmail())
                        .phone(customerRegistrationRequest.getPhone())
                        .build();
                userService.updateUser(employeeDTO);
            }
            customerRepository.save(customerEntityRecord);
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("Updating new customer " + customerId)
                            .timestamp(LocalDateTime.now())
                            .build();
            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Customer updated successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<CustomerResponse>> getAllCustomers(boolean active) {
        try {
            List<CustomerResponse> customerList = new ArrayList<>();
            customerRepository.findAllByActive(active).forEach(customer -> {
                CustomerResponse customerResponse = CustomerResponse.builder()
                        .id(customer.getId())
                        .name(customer.getUser().getName())
                        .email(customer.getUser().getEmail())
                        .phone(customer.getUser().getPhone())
                        .active(customer.isActive())
                        .preferredBank(customer.getPreferredBank())
                        .jobTitle(customer.getJobTitle())
                        .jobAddress(customer.getJobAddress())
                        .income(customer.getIncome())
                        .build();
                customerList.add(customerResponse);
            });
            return reporterService.reportSuccess(customerList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }

    @Override
    public ResponseWrapper<CustomerResponse> getCustomer(long customerId) {
        try {
            List<AddressDTO> addressList = new ArrayList<>();
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);

            if (!optionalCustomerEntity.isPresent()) {
                throw new NotFoundException("customer Not Found");
            }
            CustomerEntity customerEntityRecord = optionalCustomerEntity.get();
            LinkableImage mainPhotoEntity = LinkableImage.builder()
                    .id(0)
                    .url(customerEntityRecord.getUser().getPicUrl())
                    .build();
            customerEntityRecord.getAddresses().forEach(addressEntity ->
                    addressList.add(addressMapper.convertToDTO(addressEntity))
            );

            CustomerResponse customerResponse = CustomerResponse.builder()
                    .id(customerEntityRecord.getId())
                    .name(customerEntityRecord.getUser().getName())
                    .email(customerEntityRecord.getUser().getEmail())
                    .phone(customerEntityRecord.getUser().getPhone())
                    .active(customerEntityRecord.isActive())
                    .preferredBank(customerEntityRecord.getPreferredBank())
                    .jobTitle(customerEntityRecord.getJobTitle())
                    .jobAddress(customerEntityRecord.getJobAddress())
                    .income(customerEntityRecord.getIncome())
                    .customerPhoto(mainPhotoEntity)
                    .addresses(addressList)
                    .build();
            return reporterService.reportSuccess(customerResponse);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }
}
