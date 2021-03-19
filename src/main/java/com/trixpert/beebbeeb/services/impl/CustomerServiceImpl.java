package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.CustomerEntity;
import com.trixpert.beebbeeb.data.entites.RolesEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.mappers.CustomerMapper;
import com.trixpert.beebbeeb.data.mappers.UserMapper;
import com.trixpert.beebbeeb.data.repositories.CustomerRepository;
import com.trixpert.beebbeeb.data.repositories.RolesRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.repositories.UserRolesRepository;
import com.trixpert.beebbeeb.data.request.CustomerMobileRegistrationRequest;
import com.trixpert.beebbeeb.data.request.CustomerRegistrationRequest;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.CustomerResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.CustomerDTO;
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
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final UserRolesRepository userRolesRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    private final ReporterService reporterService;

    private final AuditService auditService;

    private final UserMapper userMapper;

    public CustomerServiceImpl(UserService userService, UserRepository userRepository,
                               RolesRepository rolesRepository, UserRolesRepository userRolesRepository,
                               CustomerRepository customerRepository, CustomerMapper customerMapper, ReporterService reporterService, AuditService auditService,
                               UserMapper userMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.userRolesRepository = userRolesRepository;
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.reporterService = reporterService;
        this.auditService = auditService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseWrapper<Boolean> registerCustomer(CustomerMobileRegistrationRequest customerRegisterRequest) {
        try {

            Optional<RolesEntity> customerRole = rolesRepository.findByName(Roles.ROLE_CUSTOMER);

            if (!customerRole.isPresent()) {
                throw new NotFoundException("Role customer Not Found");
            }

            RegistrationRequest registrationRequest = new RegistrationRequest();
            registrationRequest.setName(customerRegisterRequest.getName());
            registrationRequest.setPhone(customerRegisterRequest.getPhone());
            registrationRequest.setPassword(customerRegisterRequest.getPassword());

            UserEntity savedUser = userService.registerUser(customerRegisterRequest.getPhone(),
                    customerRole.get(), registrationRequest, true).getData();

            CustomerEntity customerEntityRecord = CustomerEntity.builder()
                    .horoscope(customerRegisterRequest.getHoroscope())
                    .user(savedUser).build();

            customerRepository.save(customerEntityRecord);
            return reporterService.reportSuccess("A new customer  has been added ");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
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
    public ResponseWrapper<Boolean> updateCustomer(CustomerRegistrationRequest customerRegistrationRequest ,
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
                    customerRegistrationRequest.getJobAddress()
                    .equals(customerEntityRecord.getJobAddress())) {
                customerEntityRecord.setJobAddress(customerRegistrationRequest.getJobAddress());
            }
            if (customerRegistrationRequest.getJobTitle() != null &&
                    customerRegistrationRequest.getJobTitle().equals(
                    customerEntityRecord.getJobTitle())) {
                customerEntityRecord.setJobTitle(customerRegistrationRequest.getJobTitle());
            }
            if (customerRegistrationRequest.getPreferredBank() != null &&
                    customerRegistrationRequest.getPreferredBank().equals(
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
            customerRepository.findAllByActive(active).forEach(customer ->{
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
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);

            if (!optionalCustomerEntity.isPresent()) {
                throw new NotFoundException("customer Not Found");
            }
            CustomerEntity customerEntityRecord = optionalCustomerEntity.get();
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
                    .build();
            return reporterService.reportSuccess(customerResponse);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }
}
