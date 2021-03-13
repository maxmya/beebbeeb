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
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.CustomerDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.CustomerService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.springframework.stereotype.Service;

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

    @Override
    public ResponseWrapper<Boolean> updateCustomer(CustomerDTO customerDTO, String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);
        try {

            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(
                    customerDTO.getId());

            if (!optionalCustomerEntity.isPresent()) {
                throw new NotFoundException("customer Not Found");
            }
            CustomerEntity customerEntityRecord = optionalCustomerEntity.get();

            if (customerDTO.getIncome() != -1 && customerDTO.getIncome() != customerEntityRecord.getIncome()) {
                customerEntityRecord.setIncome(customerDTO.getIncome());
            }
            if (customerDTO.getJobAddress() != null && customerDTO.getJobAddress()
                    .equals(customerEntityRecord.getJobAddress())) {
                customerEntityRecord.setJobAddress(customerDTO.getJobAddress());
            }
            if (customerDTO.getJobTitle() != null && customerDTO.getJobTitle().equals(
                    customerEntityRecord.getJobTitle())) {
                customerEntityRecord.setJobTitle(customerDTO.getJobTitle());
            }
            if (customerDTO.getPreferredBank() != null && customerDTO.getPreferredBank().equals(
                    customerEntityRecord.getPreferredBank())) {
                customerEntityRecord.setPreferredBank(customerDTO.getPreferredBank());
            }
            if (customerDTO.getUser() != null && customerDTO.getUser().equals(
                    customerEntityRecord.getUser())) {
                userService.updateUser(customerDTO.getUser());
            }
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("Updating new customer " + customerDTO.getId())
                            .timestamp(LocalDateTime.now())
                            .build();
            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("A new customer  has been added ");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<CustomerDTO>> getAllCustomers(boolean active) {
        try {
            List<CustomerDTO> customerList = new ArrayList<>();
            customerRepository.findAllByActive(active).forEach(customer ->
                    customerList.add(customerMapper.convertToDTO(customer)));
            return reporterService.reportSuccess(customerList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }

    @Override
    public ResponseWrapper<CustomerDTO> getCustomer(long customerId) {
        try {
            Optional<CustomerEntity> optionalCustomerEntity = customerRepository.findById(customerId);

            if (!optionalCustomerEntity.isPresent()) {
                throw new NotFoundException("customer Not Found");
            }
            CustomerEntity customerEntityRecord = optionalCustomerEntity.get();
            return reporterService.reportSuccess(customerMapper.convertToDTO(customerEntityRecord));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }
}
