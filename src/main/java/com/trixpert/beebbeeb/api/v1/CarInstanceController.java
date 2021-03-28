package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.CarInstanceRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarInstanceDTO;
import com.trixpert.beebbeeb.services.CarInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Cars Instance API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/car/instance")
public class CarInstanceController {

    private CarInstanceService carInstanceService;

    public CarInstanceController(CarInstanceService carInstanceService) {
        this.carInstanceService = carInstanceService;
    }


    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @PostMapping("/add")
    @ApiOperation("Adding a new Car Instance")
    public ResponseEntity<ResponseWrapper<Boolean>> addCarInstance(
            @Valid @RequestBody CarInstanceRequest carInstanceRequest,
            HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(carInstanceService.addCarInstance(carInstanceRequest, authorizationHeader));
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @GetMapping("/list/active")
    @ApiOperation("Get All Active Car Instances")
    public ResponseEntity<ResponseWrapper<List<CarInstanceDTO>>> getAllActiveCarInstances(){
        return ResponseEntity.ok(carInstanceService.getALLCarInstances(true));
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @GetMapping("/list/inactive")
    @ApiOperation("Get All Inactive Car Instances")
    public ResponseEntity<ResponseWrapper<List<CarInstanceDTO>>> getAllInactiveCarInstances(){
        return ResponseEntity.ok(carInstanceService.getALLCarInstances(false));
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @GetMapping("{vendorId}/list/active")
    @ApiOperation("Get All Active Car Instances for specific vendor")
    public ResponseEntity<ResponseWrapper<List<CarInstanceDTO>>> getAllActiveCarInstancesForVendor(
            @PathVariable("vendorId") long vendorId){
        return ResponseEntity.ok(carInstanceService.getAllCarInstancesForVendor(vendorId, true));
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @GetMapping("{vendorId}/list/inactive")
    @ApiOperation("Get All Inactive Car Instances for specific vendor")
    public ResponseEntity<ResponseWrapper<List<CarInstanceDTO>>> getAllInactiveCarInstancesForVendor(
            @PathVariable("vendorId") long vendorId){
        return ResponseEntity.ok(carInstanceService.getAllCarInstancesForVendor(vendorId, false));
    }
}
