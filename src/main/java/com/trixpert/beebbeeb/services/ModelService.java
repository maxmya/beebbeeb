package com.trixpert.beebbeeb.services;


import com.trixpert.beebbeeb.data.entites.BrandEntity;
import com.trixpert.beebbeeb.data.request.ModelRegisterRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.ModelDTO;

import java.util.List;


public interface ModelService {

    ResponseWrapper<Boolean> registerModel(ModelRegisterRequest modelRegisterRequest , String authHeader);

    ResponseWrapper<Boolean> updateModel (ModelDTO modelDTO , String authHeader);

    ResponseWrapper<Boolean> deleteModel (Long modelID , String authHeader);

        ResponseWrapper<List<ModelDTO>> listAllModels(boolean active);

    ResponseWrapper<List<ModelDTO>> listModelsForBrand(boolean active , long brandId);

    ResponseWrapper<List<CarDTO>> listCarsForModel(boolean active, long modelId);

}
