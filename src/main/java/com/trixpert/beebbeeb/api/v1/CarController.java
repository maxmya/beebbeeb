package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.CarRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.services.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @Valid @RequestBody CarRegistrationRequest carRegistrationRequest){
        return ResponseEntity.ok(carService.registerCar(carRegistrationRequest));
    }

    @PutMapping("/update/{carId}")
    @ApiOperation("updating a Car")
    public ResponseEntity<ResponseWrapper<Boolean>> updateCar(
            @PathVariable("carId") long carId,
            @Valid @RequestBody CarRegistrationRequest carRegistrationRequest){
        return ResponseEntity.ok(carService.updateCar(carId, carRegistrationRequest));
    }

    @PutMapping("/delete/{carId}")
    @ApiOperation("Delete a car")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteCar(@PathVariable("carId") long carId){
        return ResponseEntity.ok(carService.deleteCar(carId));
    }

    @GetMapping("/list/active")
    @ApiOperation("Get list of active cars")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getAllActiveCars(){
        return ResponseEntity.ok(carService.getAllCars(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get list of inactive cars")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getAllInactiveCars(){
        return ResponseEntity.ok(carService.getAllCars(false));
    }

    @GetMapping("/get/{carId}")
    @ApiOperation("Get car by id ")
    public ResponseEntity<ResponseWrapper<CarDTO>> getCar(@PathVariable("carId") long carId){
        return ResponseEntity.ok(carService.getCar(carId));
    }
}

