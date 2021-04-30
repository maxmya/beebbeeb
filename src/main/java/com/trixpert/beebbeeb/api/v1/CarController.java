package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.CarRegistrationRequest;
import com.trixpert.beebbeeb.data.response.FileUploadResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.services.CarService;
import com.trixpert.beebbeeb.services.ModelService;
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
    private final ModelService modelService;

    public CarController(CarService carService, ModelService modelService) {
        this.carService = carService;
        this.modelService = modelService;
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

    @GetMapping("/list/active/{year}")
    @ApiOperation("Get list of active cars for specific year")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getActiveCarsForYear(
            @PathVariable("year") String year){
        return ResponseEntity.ok(carService.listCarsForYear(true, year));
    }

    @GetMapping("/list/inactive/{year}")
    @ApiOperation("Get list of inactive cars for specific year")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getInactiveCarsForYear(
            @PathVariable("year") String year){
        return ResponseEntity.ok(carService.listCarsForYear(false, year));
    }

    @GetMapping("/list/active/{brandId}/{modelId}")
    @ApiOperation("Get list of active cars for specific brand and model")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getActiveCarsForBrandAndModel(
            @PathVariable("brandId") long brandId,
            @PathVariable("modelId") long modelId){
        return ResponseEntity.ok(carService.listCarsForBrandAndModel(true, brandId, modelId));
    }

    @GetMapping("/list/active/{brandId}")
    @ApiOperation("Get list of active cars for specific brand")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getActiveCarsForBrand(
            @PathVariable("brandId") long brandId){
        return ResponseEntity.ok(carService.listCarsForBrand(true, brandId));
    }

    @GetMapping("/list/inactive/{brandId}")
    @ApiOperation("Get list of inactive cars for specific brand")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getInactiveCarsForBrand(
            @PathVariable("brandId") long brandId){
        return ResponseEntity.ok(carService.listCarsForBrand(false, brandId));
    }

    @GetMapping("/list/active/{modelId}")
    @ApiOperation("Get list of active cars for specific model")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getActiveCarsForModel(
            @PathVariable("modelId") long modelId) {
        return ResponseEntity.ok(carService.listCarsForModel(true, modelId));
    }

    @GetMapping("/list/inactive/{modelId}")
    @ApiOperation("Get list of inactive cars for specific model")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getInactiveCarsForModel(
            @PathVariable("modelId") long modelId) {
        return ResponseEntity.ok(carService.listCarsForModel(true, modelId));
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
                                                                      @Valid @RequestBody CarRegistrationRequest carRegistrationRequest) {

        return ResponseEntity.ok(carService.updateCar(carId, carRegistrationRequest));
    }

}

