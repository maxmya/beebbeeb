package com.trixpert.beebbeeb.api.v1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trixpert.beebbeeb.data.request.BannerRegistrationRequest;
import com.trixpert.beebbeeb.data.request.TypeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.BannerDTO;
import com.trixpert.beebbeeb.data.to.TypeDTO;
import com.trixpert.beebbeeb.services.BannerService;
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

@Api(tags = {"Banner API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {
    private final BannerService bannerService;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }


    @CrossOrigin(origins = {"*"})
    @ApiOperation("Add New Banner")
    @PostMapping(value = "/add", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseBody
    public ResponseEntity<ResponseWrapper<Boolean>> addBanner(
            @RequestParam(name = "file") MultipartFile logoFile,
            @Valid @RequestParam(name = "body") String regRequest,
            HttpServletRequest request) throws IOException {

        String authorizationHeader = request.getHeader("Authorization");
        ObjectMapper objectMapper = new ObjectMapper();
        BannerRegistrationRequest bannerRegistrationRequest = objectMapper.readValue(
                regRequest, BannerRegistrationRequest.class);
        return ResponseEntity.ok(bannerService.registerBanner(
                bannerRegistrationRequest, logoFile, authorizationHeader));
    }
    @CrossOrigin(origins = {"*"})
    @PutMapping(value = "/update/{bannerId}", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation("Update an existing type with new data")
    @ResponseBody
    public  ResponseEntity<ResponseWrapper<Boolean>> Banner(
            @RequestParam(name = "file") MultipartFile logoFile,
            @Valid @RequestParam(name = "body") String regRequest,
            @PathVariable("bannerId") long bannerId
            , HttpServletRequest request) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");
        ObjectMapper objectMapper = new ObjectMapper();
        BannerRegistrationRequest bannerRegistrationRequest = objectMapper.readValue(
                regRequest, BannerRegistrationRequest.class);

        return ResponseEntity.ok(bannerService.updateBanner(bannerRegistrationRequest ,
                logoFile ,bannerId , authorizationHeader));
    }

    @PutMapping("/delete/{bannerId}")
    @ApiOperation("Remove Banner By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteType(
            @PathVariable("bannerId") long bannerId
            , HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(bannerService.deleteBanner(bannerId, authorizationHeader));
    }

    @GetMapping("/get/{bannerId}")
    @ApiOperation("Get banner by Id")
    public ResponseEntity<ResponseWrapper<BannerDTO>> getType(@PathVariable("bannerId")long bannerId){
        return ResponseEntity.ok(bannerService.getBanner(bannerId));
    }

    @GetMapping("/list/active")
    @ApiOperation("Get all active Banners List")
    public ResponseEntity<ResponseWrapper<List<BannerDTO>>> getActiveBanners() {
        return ResponseEntity.ok(bannerService.listAllBanners(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get all non-active Banners List")
    public ResponseEntity<ResponseWrapper<List<BannerDTO>>> getInActiveBanners() {
        return ResponseEntity.ok(bannerService.listAllBanners(false));
    }


}
