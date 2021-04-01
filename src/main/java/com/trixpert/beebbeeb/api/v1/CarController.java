package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.CarInstanceRequest;
import com.trixpert.beebbeeb.data.request.CarRegistrationRequest;
import com.trixpert.beebbeeb.data.response.FileUploadResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.services.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = {"Cars API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/add")
    @ApiOperation("Adding a new car")
    public ResponseEntity<ResponseWrapper<Boolean>> registerCar(
            @Valid @RequestBody CarRegistrationRequest carRegistrationRequest,
            HttpServletRequest request
    ) {
        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(carService.registerCar(carRegistrationRequest, authorizationHeader));
    }

    @PostMapping("/interior/{carId}")
    @ApiOperation("Add Interior Photo For Car")
    public ResponseEntity<ResponseWrapper<FileUploadResponse>> uploadInterior(
            @PathVariable("carId") long modelId,
            @RequestParam("file") MultipartFile image,
            HttpServletRequest request) {
        return ResponseEntity.ok(carService.uploadInterior(modelId, image));
    }

    @PostMapping("/exterior/{carId}")
    @ApiOperation("Add Interior Photo For Car")
    public ResponseEntity<ResponseWrapper<FileUploadResponse>> uploadExterior(
            @PathVariable("carId") long modelId,
            @RequestParam("file") MultipartFile image,
            HttpServletRequest request) {
        return ResponseEntity.ok(carService.uploadExterior(modelId, image));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping("/delete/{carId}")
    @ApiOperation("Delete a car")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteCar(@PathVariable("carId") long carId) {
        System.out.println(carId);
        return ResponseEntity.ok(carService.deleteCar(carId));
    }

    @GetMapping("/list/active")
    @ApiOperation("Get list of active cars")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getAllActiveCars() {
        return ResponseEntity.ok(carService.getAllCars(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get list of inactive cars")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getAllInactiveCars() {
        return ResponseEntity.ok(carService.getAllCars(false));
    }

    @GetMapping("/get/{carId}")
    @ApiOperation("Get car by id ")
    public ResponseEntity<ResponseWrapper<CarDTO>> getCar(@PathVariable("carId") long carId) {
        return ResponseEntity.ok(carService.getCar(carId));
    }


    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping("update/{carId}")
    @ApiOperation("Update Car ")
    public ResponseEntity<ResponseWrapper<Boolean>> updateCarInstance(@PathVariable("carId") long carId,
                                                                      @Valid @RequestBody CarRegistrationRequest carRegistrationRequest){

        return ResponseEntity.ok(carService.updateCar(carId,carRegistrationRequest));
    }

}

