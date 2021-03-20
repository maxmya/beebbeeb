package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.BannerRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.BannerDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BannerService {

    ResponseWrapper<Boolean> registerBanner (BannerRegistrationRequest bannerRegistrationRequest
           , MultipartFile logoFile , String authHeader) throws IOException;;

    ResponseWrapper<Boolean> deleteBanner(long bannerId, String authHeader);

    ResponseWrapper<Boolean> updateBanner(BannerRegistrationRequest bannerRegistrationRequest,
                                          MultipartFile logoFile ,
                                          long bannerId, String authHeader)throws IOException; ;

    ResponseWrapper<BannerDTO> getBanner(long bannerId);

    ResponseWrapper<List<BannerDTO>> listAllBanners(boolean active);

}
