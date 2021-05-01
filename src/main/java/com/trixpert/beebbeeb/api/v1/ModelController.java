package com.trixpert.beebbeeb.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.ModelRegisterRequest;
import com.trixpert.beebbeeb.data.response.FileUploadResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.ModelDTO;
import com.trixpert.beebbeeb.services.ModelService;
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

@Api(tags = {"Models API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/models")

public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @CrossOrigin(origins = {"*"})
    @PostMapping(value = "/register", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("Register New Model")
    public ResponseEntity<ResponseWrapper<Boolean>> registerModel(
            @RequestParam("file") MultipartFile images,
            @Valid @RequestParam("body") String modelRegisterRequest
            , HttpServletRequest request) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ModelRegisterRequest registerRequest = objectMapper.readValue(
                modelRegisterRequest, ModelRegisterRequest.class);
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(modelService.registerModel(images, registerRequest, authorizationHeader));
    }

    @PostMapping("/interior/{modelId}")
    @ApiOperation("Add Interior Photo For Model")
    public ResponseEntity<ResponseWrapper<FileUploadResponse>> uploadInterior(
            @PathVariable("modelId") long modelId,
            @RequestParam("file") MultipartFile image,
            HttpServletRequest request) {
        return ResponseEntity.ok(modelService.uploadInterior(modelId, image));
    }

    @PostMapping("/exterior/{modelId}")
    @ApiOperation("Add Interior Photo For Model")
    public ResponseEntity<ResponseWrapper<FileUploadResponse>> uploadExterior(
            @PathVariable("modelId") long modelId,
            @RequestParam("file") MultipartFile image,
            HttpServletRequest request) {
        return ResponseEntity.ok(modelService.uploadExterior(modelId, image));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping(value = "/update/{modelId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("Update Model")
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> updateModel(
            @PathVariable("modelId") long modelId,
            @RequestParam("file") MultipartFile images,
            @Valid @RequestParam("body") String modelRegisterRequest
            , HttpServletRequest request)throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ModelRegisterRequest registerRequest = objectMapper.readValue(
                modelRegisterRequest, ModelRegisterRequest.class);
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(modelService.updateModel(modelId, images, registerRequest, authorizationHeader));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN','ROLE_ADMIN')")
    @PutMapping("/delete/{modelId}")
    @ApiOperation("Delete Model By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteModel(
            @PathVariable("modelId") long modelId
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
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getActiveCarsForModel(
            @PathVariable("modelId") long modelId) {
        return ResponseEntity.ok(modelService.listCarsForModel(true, modelId));
    }

    @GetMapping("/cars/list/inactive/{modelId}")
    @ApiOperation("Get list of inactive cars for specific model")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> getInactiveCarsForModel(
            @PathVariable("modelId") long modelId) {
        return ResponseEntity.ok(modelService.listCarsForModel(false, modelId));
    }

    @GetMapping("/Get/{modelId}")
    @ApiOperation("Get model by Id")
    public ResponseEntity<ResponseWrapper<ModelDTO>> getModel(@PathVariable("modelId") long modelId) {
        return ResponseEntity.ok(modelService.getModel(modelId));
    }
}

