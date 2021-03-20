package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.CarRegistrationRequest;
import com.trixpert.beebbeeb.data.response.FileUploadResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {

    ResponseWrapper<Boolean> registerCar(CarRegistrationRequest carRegistrationRequest, String authHeader);


    ResponseWrapper<FileUploadResponse> uploadInterior(long carId, MultipartFile file);

    ResponseWrapper<FileUploadResponse> uploadExterior(long carId, MultipartFile file);


    ResponseWrapper<Boolean> deleteCar(long carId);

    ResponseWrapper<List<CarDTO>> getAllCars(boolean active);

    ResponseWrapper<CarDTO> getCar(long carId);

}
