package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.EmployeeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.EmployeeDTO;
import com.trixpert.beebbeeb.services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Employees API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/vendor/branch/add")
    @ApiOperation("Adding a new employee to a specific branch")
    public ResponseEntity<ResponseWrapper<Boolean>> addEmployeeForBranch(
            EmployeeRegistrationRequest employeeRegistrationRequest ,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(employeeService.addEmployeeForBranch(employeeRegistrationRequest ,
                authorizationHeader));
    }

    @GetMapping("{branchId}/list/active")
    @ApiOperation("Get list of active employees for a specific branch")
    public ResponseEntity<ResponseWrapper<List<EmployeeDTO>>> getAllActiveEmployeesForBranch(@PathVariable("branchId") Long branchId) {
        return ResponseEntity.ok(employeeService.getAllEmployeesForBranch(true, branchId));
    }

    @GetMapping("{branchId}/list/inactive")
    @ApiOperation("Get list of inactive employees for a specific branch")
    public ResponseEntity<ResponseWrapper<List<EmployeeDTO>>> getAllInactiveEmployeesForBranch(@PathVariable("branchId") Long branchId) {
        return ResponseEntity.ok(employeeService.getAllEmployeesForBranch(false, branchId));
    }

    @PutMapping("/update")
    @ApiOperation("Update an existing employee")
    public ResponseEntity<ResponseWrapper<Boolean>> updateEmployeeForBranch(
            @Valid @RequestBody EmployeeRegistrationRequest employeeRegistrationRequest,
            long employeeId, HttpServletRequest request){

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(employeeService.updateEmployeeForBranch(employeeRegistrationRequest,
                employeeId, authorizationHeader));
    }

    @PutMapping("/delete/{employeeId}")
    @ApiOperation("delete an employee")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteEmployeeForBranch(
                @PathVariable("employeeId") Long employeeId
                , HttpServletRequest request){

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(employeeService.deleteEmployeeForBranch(employeeId , authorizationHeader));
    }

    @GetMapping("{vendorId}/list/active")
    @ApiOperation("Get list of all active employees for a specific vendor")
    public ResponseEntity<ResponseWrapper<List<EmployeeDTO>>> getAllActiveEmployeesForVendor(@PathVariable("vendorId") Long vendorId){
        return ResponseEntity.ok(employeeService.getAllEmployeesForVendor(true, vendorId));
    }

    @GetMapping("{vendorId}/list/inactive")
    @ApiOperation("Get list of all active employees for a specific vendor")
    public ResponseEntity<ResponseWrapper<List<EmployeeDTO>>> getAllInactiveEmployeesForVendor(@PathVariable("vendorId") Long vendorId){
        return ResponseEntity.ok(employeeService.getAllEmployeesForVendor(false, vendorId));
    }

    @GetMapping("Get/{employeeId}")
    @ApiOperation("Get employee by Id")
    public ResponseEntity<ResponseWrapper<EmployeeDTO>> getEmployee(@PathVariable("employeeId")
                                                                    long employeeId){
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }
}
