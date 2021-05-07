package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.CarInstanceRequest;
import com.trixpert.beebbeeb.data.request.ReviewRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.response.ReviewResponse;
import com.trixpert.beebbeeb.data.to.CarInstanceDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarInstanceService {

    ResponseWrapper<Boolean> addCarInstance(CarInstanceRequest carInstanceRequest );

    ResponseWrapper<List<CarInstanceDTO>> getALLCarInstances(boolean active);

    ResponseWrapper<List<CarInstanceDTO>> getAllCarInstancesForVendor(long vendorId, boolean active);

    ResponseWrapper<Boolean> deleteCarInstance(long carInstanceId);

    ResponseWrapper<Boolean> updateCarInstance(long carInstanceId , CarInstanceRequest carInstanceRequest);

    ResponseWrapper<Boolean> addBrochure(MultipartFile brochureFile , long carInstanceId) throws IOException;

    ResponseWrapper<Boolean> addReviewForCarInstance(long carInstanceId, ReviewRegistrationRequest reviewRegistrationRequest);

    ResponseWrapper<List<ReviewResponse>> getUserReviewsForCarInstance (long carInstanceId, String authHeader);
}
