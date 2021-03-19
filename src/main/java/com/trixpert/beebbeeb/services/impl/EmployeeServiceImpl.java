package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.constants.Roles;
import com.trixpert.beebbeeb.data.entites.BranchEntity;
import com.trixpert.beebbeeb.data.entites.EmployeeEntity;
import com.trixpert.beebbeeb.data.entites.RolesEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.mappers.EmployeeMapper;
import com.trixpert.beebbeeb.data.repositories.BranchRepository;
import com.trixpert.beebbeeb.data.repositories.EmployeeRepository;
import com.trixpert.beebbeeb.data.repositories.RolesRepository;
import com.trixpert.beebbeeb.data.repositories.VendorRepository;
import com.trixpert.beebbeeb.data.request.EmployeeRegistrationRequest;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.EmployeeDTO;
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
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final RolesRepository rolesRepository;
    private final VendorRepository vendorRepository;

    private final ReporterService reporterService;
    private final UserService userService;
    private final AuditService auditService;


    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               BranchRepository branchRepository,
                               RolesRepository rolesRepository,
                               VendorRepository vendorRepository,
                               ReporterService reporterService,
                               UserService userService,
                               EmployeeMapper employeeMapper,
                               AuditService auditService) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.rolesRepository = rolesRepository;
        this.vendorRepository = vendorRepository;
        this.reporterService = reporterService;
        this.userService = userService;
        this.employeeMapper = employeeMapper;
        this.auditService = auditService;
    }

    @Override
    public ResponseWrapper<Boolean> addEmployeeForBranch(
            EmployeeRegistrationRequest employeeRegistrationRequest,
            String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BranchEntity> optionalBranchRecord = branchRepository.findById(employeeRegistrationRequest.getBranchId());
            if (!optionalBranchRecord.isPresent()) {
                throw new NotFoundException(" Branch Entity not found");
            }
            BranchEntity branchRecord = optionalBranchRecord.get();

            RegistrationRequest registrationRequest = new RegistrationRequest(
                    employeeRegistrationRequest.getName(),
                    employeeRegistrationRequest.getPhone(),
                    employeeRegistrationRequest.getEmail(),
                    employeeRegistrationRequest.isActive(),
                    employeeRegistrationRequest.getPassword()
            );

            Optional<RolesEntity> employeesRole = rolesRepository.findByName(Roles.ROLE_EMPLOYEE);

            if (!employeesRole.isPresent()) {
                throw new NotFoundException("Employees Role Not Found !");
            }

            UserEntity userEntity = userService.registerUser(
                    employeeRegistrationRequest.getEmail(),
                    employeesRole.get(), registrationRequest, false).getData();

            EmployeeEntity employeeRecord = EmployeeEntity.builder()
                    .title(employeeRegistrationRequest.getTitle())
                    .user(userEntity)
                    .branch(branchRecord)
                    .active(employeeRegistrationRequest.isActive())
                    .build();
            employeeRepository.save(employeeRecord);
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Adding new branch Employee " + employeeRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);
            return reporterService.reportSuccess("A new employee has been added to the branch with the id"
                    .concat(Long.toString(branchRecord.getId())));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<EmployeeDTO>> getAllEmployeesForBranch(boolean active, Long branchId) {
        try {
            List<EmployeeDTO> activeEmployeeList = new ArrayList<>();
            employeeRepository.findAllByBranchAndActive(branchRepository.getOne(branchId), active).forEach(employee ->
                    activeEmployeeList.add(employeeMapper.convertToDTO(employee))
            );
            return reporterService.reportSuccess(activeEmployeeList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
    @Transactional
    @Override
    public ResponseWrapper<Boolean> updateEmployeeForBranch(EmployeeRegistrationRequest employeeRegistrationRequest,
                                                            long employeeId,
                                                            String authHeader) {
        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<EmployeeEntity> optionalEmployeeRecord = employeeRepository.findById(employeeId);
            if (!optionalEmployeeRecord.isPresent()) {
                throw new NotFoundException("Employee entity not found");
            }
            EmployeeEntity employeeRecord = optionalEmployeeRecord.get();
            if (employeeRegistrationRequest.getTitle() != null) {
                employeeRecord.setTitle(employeeRegistrationRequest.getTitle());
            }
            if (employeeRegistrationRequest.getName() != null || employeeRegistrationRequest.getEmail() != null
                    || employeeRegistrationRequest.getPhone() != null) {
                UserDTO employeeDTO = UserDTO.builder()
                        .id(employeeRecord.getUser().getId())
                        .name(employeeRegistrationRequest.getName())
                        .email(employeeRegistrationRequest.getEmail())
                        .phone(employeeRegistrationRequest.getPhone())
                        .build();
                userService.updateUser(employeeDTO);
            }
            if (employeeRegistrationRequest.getBranchId() != -1 &&
                    employeeRegistrationRequest.getBranchId() != employeeRecord.getBranch().getId()) {
                Optional<BranchEntity> optionalBranchRecord = branchRepository.findById(employeeRegistrationRequest.getBranchId());
                if (!optionalBranchRecord.isPresent()) {
                    throw new NotFoundException(" Branch Entity not found");
                }
                employeeRecord.setBranch(optionalBranchRecord.get());
            }
            employeeRepository.save(employeeRecord);
            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("updating new branch Employee " + employeeRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Employee updated successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> deleteEmployeeForBranch(Long employeeId, String authHeader) {
        try {
            Optional<EmployeeEntity> optionalEmployeeRecord = employeeRepository.findById(employeeId);
            if (!optionalEmployeeRecord.isPresent()) {
                throw new NotFoundException("Employee entity not found");
            }
            EmployeeEntity employeeRecord = optionalEmployeeRecord.get();
            employeeRecord.setActive(false);

            employeeRepository.save(employeeRecord);

            String username = auditService.getUsernameForAudit(authHeader);

            AuditDTO auditDTO = AuditDTO.builder()
                    .user(userService.getUserByUsername(username))
                    .action(AuditActions.DELETE)
                    .description("Deleting new branch Employee " + employeeRecord.toString())
                    .timestamp(LocalDateTime.now())
                    .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Employee soft deleted(deActivated) successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<EmployeeDTO> getEmployee(long employeeId) {
        try {
            Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(employeeId);
            if (!optionalEmployeeEntity.isPresent()) {
                throw new Exception("this employee does not exist");
            }
            EmployeeEntity employeeEntity = optionalEmployeeEntity.get();
            return reporterService.reportSuccess(employeeMapper.convertToDTO(employeeEntity));

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<EmployeeDTO>> getAllEmployeesForVendor(boolean active, Long vendorId) {
        try {
            List<EmployeeDTO> employeesList = new ArrayList<>();
            branchRepository.findAllByVendorAndActive(vendorRepository.getOne(vendorId), true).forEach(branch ->
                    employeeRepository.findAllByBranchAndActive(branch, active).forEach(employee ->
                            employeesList.add(employeeMapper.convertToDTO(employee))
                    )
            );
            return reporterService.reportSuccess(employeesList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
