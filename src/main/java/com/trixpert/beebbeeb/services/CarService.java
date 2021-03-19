package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.CarRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;

import java.util.List;

public interface CarService {
    ResponseWrapper<Boolean> registerCar(CarRegistrationRequest carRegistrationRequest);

    ResponseWrapper<Boolean> updateCar(long carId, CarRegistrationRequest carRegistrationRequest);

    ResponseWrapper<Boolean> deleteCar(long carId);

    ResponseWrapper<List<CarDTO>> getAllCars(boolean active);

    ResponseWrapper<CarDTO> getCar(long carId);
}
