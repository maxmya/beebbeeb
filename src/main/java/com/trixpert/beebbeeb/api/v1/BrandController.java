package com.trixpert.beebbeeb.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.BrandRegisterRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.BrandDTO;
import com.trixpert.beebbeeb.services.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Api(tags = {"Brands API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }


    @GetMapping("/list/active")
    @ApiOperation("Get Brands List")
    public ResponseEntity<ResponseWrapper<List<BrandDTO>>> getActiveBrands() {

        return ResponseEntity.ok(brandService.getAllBrands(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get Brands List")
    public ResponseEntity<ResponseWrapper<List<BrandDTO>>> getInactiveBrands() {

        return ResponseEntity.ok(brandService.getAllBrands(false));
    }

    @PutMapping("/update")
    @ApiOperation("Update an existing brand with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> updateBrand(
            @RequestPart(name = "file") MultipartFile logoFile,
            @RequestPart(name = "body") BrandDTO brandDTO,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(brandService.updateBrand(logoFile, brandDTO, authorizationHeader));
    }

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                                                MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation("Add New Brand")
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> addBrand(
            @RequestParam(name = "file") MultipartFile logoFile,
            @Valid @RequestParam(name = "body") String regRequest,
            HttpServletRequest request) throws IOException {

        String authorizationHeader = request.getHeader("Authorization");
        BrandRegisterRequest brandRegisterRequest = new ObjectMapper()
                .readValue(regRequest, BrandRegisterRequest.class);
        return ResponseEntity.ok(brandService.registerBrand(
                logoFile, brandRegisterRequest, authorizationHeader));
    }

    @PutMapping("/delete/{brandID}")
    @ApiOperation("Remove Brand By ID")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteBrand(@PathVariable("brandID") Long brandId
            , HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(brandService.deleteBrand(brandId, authorizationHeader));
    }

    @GetMapping("/get/{brandID}")
    @ApiOperation("Get Brand By ID")
    public ResponseEntity<ResponseWrapper<BrandDTO>> getBrand(
            @PathVariable("brandID") long brandId,
            @RequestParam boolean active ) {
        return ResponseEntity.ok(brandService.getBrand(brandId));
    }

    @GetMapping("/list/active/{brandId}")
    @ApiOperation("Get inActive Brand")
    public ResponseEntity<ResponseWrapper<BrandDTO>> getActiveBrand(
            @PathVariable("brandId ") Long brandId) {

        return ResponseEntity.ok(brandService.getBrand(brandId));
    }

    @GetMapping("/list/inactive/{brandId}")
    @ApiOperation("Get Active Brand")
    public ResponseEntity<ResponseWrapper<BrandDTO>> getInactiveBrand(
            @PathVariable("brandId ") Long brandId) {

        return ResponseEntity.ok(brandService.getBrand(brandId));
    }
}
