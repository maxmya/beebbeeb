package com.trixpert.beebbeeb.api.v1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.ModelRegisterRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.ModelDTO;
import com.trixpert.beebbeeb.services.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"Models API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/models")

public class ModelController {

    private final ModelService modelService;


    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping("/register")
    @ApiOperation("Register New Model")
    public ResponseEntity<ResponseWrapper<Boolean>> registerModel(
            @RequestParam("file") MultipartFile images,
            @RequestParam("body") String modelRegisterRequest
            , HttpServletRequest request) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ModelRegisterRequest registerRequest = objectMapper.readValue(modelRegisterRequest, ModelRegisterRequest.class);
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(modelService.registerModel(images, registerRequest, authorizationHeader));
    }


    @PutMapping("/update")
    @ApiOperation("Update Model")
    public ResponseEntity<ResponseWrapper<Boolean>> updateModel(@Valid @RequestBody ModelDTO modelDTO
            , HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(modelService.updateModel(modelDTO, authorizationHeader));
    }


    @PutMapping("/delete/{modelId}")
    @ApiOperation("Delete Model By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteModel(@PathVariable("modelId") long modelId
            , HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(modelService.deleteModel(modelId, authorizationHeader));
    }


    @GetMapping("/list/active")
    @ApiOperation("Get Active Model List")
    public ResponseEntity<ResponseWrapper<List<ModelDTO>>> getActiveModels() {
        return ResponseEntity.ok(modelService.listAllModels(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get InActive Models ")
    public ResponseEntity<ResponseWrapper<List<ModelDTO>>> getInactiveModels() {
        return ResponseEntity.ok(modelService.listAllModels(false));
    }

    @GetMapping("/list/active/{brandId}")
    @ApiOperation("Get Model List For brand")
    public ResponseEntity<ResponseWrapper<List<ModelDTO>>> getActiveModelsForBrand(
            @PathVariable("brandId") long brandId) {
        return ResponseEntity.ok(modelService.listModelsForBrand(true, brandId));
    }

    @GetMapping("/list/inactive/{brandId}")
    @ApiOperation("Get InActive Model List for brand")
    public ResponseEntity<ResponseWrapper<List<ModelDTO>>> getInactiveModelsForBrand(
            @PathVariable("brandId") long brandId) {
        return ResponseEntity.ok(modelService.listModelsForBrand(false, brandId));
    }

    @GetMapping("/cars/list/active/{modelId}")
    @ApiOperation("Get list of active cars for specific model")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getActiveCarsForModel(@PathVariable("modelId") long modelId) {
        return ResponseEntity.ok(modelService.listCarsForModel(true, modelId));
    }

    @GetMapping("/cars/list/inactive/{modelId}")
    @ApiOperation("Get list of inactive cars for specific model")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getInactiveCarsForModel(@PathVariable("modelId") long modelId) {
        return ResponseEntity.ok(modelService.listCarsForModel(false, modelId));
    }
    @GetMapping("/Get/{modelId}")
    @ApiOperation("Get model by Id")
    public ResponseEntity<ResponseWrapper<ModelDTO>> getModel(@PathVariable("modelId")long modelId){
        return ResponseEntity.ok(modelService.getModel(modelId));
    }
}

