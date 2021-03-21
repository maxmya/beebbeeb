package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.CarSpecsDeleteRequest;
import com.trixpert.beebbeeb.data.request.CarSpecsRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.SpecsDTO;
import com.trixpert.beebbeeb.services.CarSpecsService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = {"Cars API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/specs")
public class CarSpecsController {

    private final CarSpecsService carSpecsService;

    public CarSpecsController(CarSpecsService carSpecsService) {
        this.carSpecsService = carSpecsService;
    }

    @GetMapping("/list/essentials/{carId}")
    private ResponseEntity<ResponseWrapper<List<SpecsDTO>>> getCarEssentialSpecs(
            @PathVariable("carId") long carId) {
        return ResponseEntity.ok(carSpecsService.getEssentialCarSpecs(carId));
    }


    @GetMapping("/list/extra/{carId}")
    private ResponseEntity<ResponseWrapper<List<SpecsDTO>>> getCarExtraSpecs(
            @PathVariable("carId") long carId) {
        return ResponseEntity.ok(carSpecsService.getExtraCarSpecs(carId));
    }

    @PostMapping("/add/essential")
    private ResponseEntity<ResponseWrapper<Boolean>> addCarEssentialSpecs(@RequestBody CarSpecsRequest carSpecsRequest) {
        carSpecsRequest.setEssential(true);
        return ResponseEntity.ok(carSpecsService.addCarSpecs(carSpecsRequest));
    }

    @PostMapping("/add/extra")
    private ResponseEntity<ResponseWrapper<Boolean>> addCarExtraSpecs(@RequestBody CarSpecsRequest carSpecsRequest) {
        carSpecsRequest.setEssential(false);
        return ResponseEntity.ok(carSpecsService.addCarSpecs(carSpecsRequest));
    }

    @PutMapping("/delete")
    private ResponseEntity<ResponseWrapper<Boolean>> deleteCarSpecs(@RequestBody CarSpecsDeleteRequest request) {
        return ResponseEntity.ok(carSpecsService.deleteCarSpecs(request));
    }
}

