package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.EmployeeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    ResponseWrapper<Boolean> addEmployeeForBranch(EmployeeRegistrationRequest employeeRegistrationRequest
        , String authHeader);

    ResponseWrapper<List<EmployeeDTO>> getAllEmployeesForBranch(boolean active, Long branchId);

    ResponseWrapper<List<EmployeeDTO>> getAllEmployeesForVendor(boolean active, Long vendorId);

    ResponseWrapper<Boolean> updateEmployeeForBranch(EmployeeDTO employeeDTO , String authHeader);

    ResponseWrapper<Boolean> deleteEmployeeForBranch(Long employeeId , String authHeader);
}
