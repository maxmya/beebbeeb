package com.trixpert.beebbeeb.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.CarInstanceRequest;
import com.trixpert.beebbeeb.data.request.TypeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarInstanceDTO;
import com.trixpert.beebbeeb.services.CarInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"Cars Instance API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/car/instance")
public class CarInstanceController {

    private final CarInstanceService carInstanceService;

    public CarInstanceController(CarInstanceService carInstanceService) {
        this.carInstanceService = carInstanceService;
    }


    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PostMapping("/add")
    @ApiOperation("Adding a new Car Instance")
    public ResponseEntity<ResponseWrapper<Boolean>> addCarInstance(
            @Valid @RequestBody CarInstanceRequest carInstanceRequest) {
        return ResponseEntity.ok(carInstanceService.addCarInstance(carInstanceRequest));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("/list/active")
    @ApiOperation("Get All Active Car Instances")
    public ResponseEntity<ResponseWrapper<List<CarInstanceDTO>>> getAllActiveCarInstances() {
        return ResponseEntity.ok(carInstanceService.getALLCarInstances(true));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("/list/inactive")
    @ApiOperation("Get All Inactive Car Instances")
    public ResponseEntity<ResponseWrapper<List<CarInstanceDTO>>> getAllInactiveCarInstances() {
        return ResponseEntity.ok(carInstanceService.getALLCarInstances(false));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("{vendorId}/list/active")
    @ApiOperation("Get All Active Car Instances for specific vendor")
    public ResponseEntity<ResponseWrapper<List<CarInstanceDTO>>> getAllActiveCarInstancesForVendor(
            @PathVariable("vendorId") long vendorId) {
        return ResponseEntity.ok(carInstanceService.getAllCarInstancesForVendor(vendorId, true));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @GetMapping("{vendorId}/list/inactive")
    @ApiOperation("Get All Inactive Car Instances for specific vendor")
    public ResponseEntity<ResponseWrapper<List<CarInstanceDTO>>> getAllInactiveCarInstancesForVendor(
            @PathVariable("vendorId") long vendorId) {
        return ResponseEntity.ok(carInstanceService.getAllCarInstancesForVendor(vendorId, false));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping("delete/{carInstanceId}")
    @ApiOperation("Delete Car Instance By ID")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteCarInstance(@PathVariable("carInstanceId") long carInstanceId) {
        return ResponseEntity.ok(carInstanceService.deleteCarInstance(carInstanceId));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping("update/{carInstanceId}")
    @ApiOperation("Update Car Instance")
    public ResponseEntity<ResponseWrapper<Boolean>> updateCarInstance(@PathVariable("carInstanceId") long carInstanceId,
                                                                      @Valid @RequestBody CarInstanceRequest carInstanceRequest) {

        return ResponseEntity.ok(carInstanceService.updateCarInstance(carInstanceId, carInstanceRequest));
    }


    @CrossOrigin(origins = {"*"})
    @ApiOperation("Add New Brochure File to car instance")
    @PostMapping(value = "/add/brochure/{carInstanceId}", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> addBrochure(
            @RequestParam(name = "file") MultipartFile brochureFIle,
            @PathVariable("carInstanceId") long carInstanceId,
            HttpServletRequest request) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(carInstanceService.addBrochure(brochureFIle,carInstanceId));
    }


}
