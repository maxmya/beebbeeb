package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.TypeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.TypeDTO;
import com.trixpert.beebbeeb.services.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
        return ResponseEntity.ok(typeService.listAllTypes(true));
    }


    @PutMapping("/update")
    @ApiOperation("Update an existing type with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> updateType(
            @RequestPart(name = "body") TypeDTO typeDTO, HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(typeService.updateType(typeDTO, authorizationHeader));
    }

    @PostMapping("/add")
    @ApiOperation("Add New Type")
    public ResponseEntity<ResponseWrapper<Boolean>> addType(
            @RequestPart(name = "body") TypeRegistrationRequest typeRegistrationRequest,
            @RequestPart(name = "file") MultipartFile logoFile,
            HttpServletRequest request) throws IOException {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(typeService.addType(typeRegistrationRequest, logoFile, authorizationHeader));
    }

    @PutMapping("/delete/{typeId}")
    @ApiOperation("Remove Type By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteType(
            @PathVariable("typeId") Long typeId
            , HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(typeService.deleteType(typeId, authorizationHeader));
    }
}