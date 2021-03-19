package com.trixpert.beebbeeb.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.TypeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.TypeDTO;
import com.trixpert.beebbeeb.services.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"Type API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/types")
public class TypeController {


    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }


    @GetMapping("/list/active")
    @ApiOperation("Get all active Types List")
    public ResponseEntity<ResponseWrapper<List<TypeDTO>>> getActiveTypes() {
        return ResponseEntity.ok(typeService.listAllTypes(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get all non-active Types List")
    public ResponseEntity<ResponseWrapper<List<TypeDTO>>> getInActiveTypes() {
        return ResponseEntity.ok(typeService.listAllTypes(false));
    }

    @CrossOrigin(origins = {"*"})
    @PutMapping(value = "/update/{typeId}", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("Update an existing type with new data")
    @ResponseBody
    public  ResponseEntity<ResponseWrapper<Boolean>> updateType(
            @RequestParam(name = "file") MultipartFile logoFile,
            @Valid @RequestParam(name = "body") String regRequest,
            @PathVariable("typeId") long typeId
            , HttpServletRequest request) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        ObjectMapper objectMapper = new ObjectMapper();
        TypeRegistrationRequest typeRegistrationRequest = objectMapper.readValue(
                regRequest, TypeRegistrationRequest.class);

        return ResponseEntity.ok(typeService.updateType(typeRegistrationRequest ,
                typeId, logoFile , authorizationHeader));
    }

    @CrossOrigin(origins = {"*"})
    @ApiOperation("Add New Type")
    @PostMapping(value = "/add", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> addType(
            @RequestParam(name = "file") MultipartFile logoFile,
            @Valid @RequestParam(name = "body") String regRequest,
            HttpServletRequest request) throws IOException {

        String authorizationHeader = request.getHeader("Authorization");
        ObjectMapper objectMapper = new ObjectMapper();
        TypeRegistrationRequest typeRegistrationRequest = objectMapper.readValue(
                regRequest, TypeRegistrationRequest.class);
        return ResponseEntity.ok(typeService.addType(
                typeRegistrationRequest, logoFile, authorizationHeader));
    }

    @PutMapping("/delete/{typeId}")
    @ApiOperation("Remove Type By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteType(
            @PathVariable("typeId") Long typeId
            , HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(typeService.deleteType(typeId, authorizationHeader));
    }

    @GetMapping("/get/{typeId}")
    @ApiOperation("Get type by Id")
    public ResponseEntity<ResponseWrapper<TypeDTO>> getType(@PathVariable("typeId")long typeId){
        return ResponseEntity.ok(typeService.getType(typeId));
    }
}