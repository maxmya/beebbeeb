package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.BrandRegisterRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.BrandDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BrandService {

    ResponseWrapper<Boolean> registerBrand(MultipartFile logoFile, BrandRegisterRequest brandRegisterRequest, String authHeader) throws IOException;

    ResponseWrapper<Boolean> deleteBrand(long brandId , String authHeader);

    ResponseWrapper<Boolean> updateBrand(MultipartFile logoFile, BrandRegisterRequest brandRegisterRequest
            , long brandId, String authHeader);

    ResponseWrapper<List<BrandDTO>> getAllBrands(boolean active);
 
    ResponseWrapper<BrandDTO> getBrand(long brandId);
 
}
