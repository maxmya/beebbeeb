package com.trixpert.beebbeeb.services;


import com.trixpert.beebbeeb.data.request.ModelRegisterRequest;
import com.trixpert.beebbeeb.data.response.FileUploadResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.ModelDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ModelService {

    ResponseWrapper<Boolean> registerModel(MultipartFile img,
                                           ModelRegisterRequest modelRegisterRequest,
                                           String authHeader) throws IOException;

    ResponseWrapper<FileUploadResponse> uploadInterior(long modelId, MultipartFile file);

    ResponseWrapper<FileUploadResponse> uploadExterior(long modelId, MultipartFile file);

    ResponseWrapper<Boolean> updateModel(long modelId,
                                         MultipartFile img,
                                         ModelRegisterRequest modelRegisterRequest,
                                         String authHeader);

    ResponseWrapper<Boolean> deleteModel(long modelId, String authHeader);

    ResponseWrapper<List<ModelDTO>> listAllModels(boolean active);

    ResponseWrapper<List<ModelDTO>> listModelsForBrand(boolean active, long brandId);

    ResponseWrapper<List<CarDTO>> listCarsForModel(boolean active, long modelId);

    ResponseWrapper<ModelDTO> getModel(long modelId);

}
