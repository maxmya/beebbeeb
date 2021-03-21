package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.CarSpecsDeleteRequest;
import com.trixpert.beebbeeb.data.request.CarSpecsRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.SpecsDTO;

import java.util.List;

public interface CarSpecsService {

    ResponseWrapper<List<SpecsDTO>> getEssentialCarSpecs(long carId);

    ResponseWrapper<List<SpecsDTO>> getExtraCarSpecs(long carId);

    ResponseWrapper<Boolean> addCarSpecs(CarSpecsRequest specsRequest);

    ResponseWrapper<Boolean> deleteCarSpecs(CarSpecsDeleteRequest specsRequest);


}
