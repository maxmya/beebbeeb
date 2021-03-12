package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.BrandEntity;
import com.trixpert.beebbeeb.data.entites.ModelEntity;
import com.trixpert.beebbeeb.data.mappers.BrandMapper;
import com.trixpert.beebbeeb.data.mappers.CarMapper;
import com.trixpert.beebbeeb.data.mappers.ModelMapper;
import com.trixpert.beebbeeb.data.repositories.BrandRepository;
import com.trixpert.beebbeeb.data.repositories.ModelRepository;
import com.trixpert.beebbeeb.data.request.ModelRegisterRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.AuditDTO;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.ModelDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.ModelService;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    private final BrandRepository brandRepository;


    private final ModelMapper modelMapper;

    private final ReporterService reporterService;

    private final BrandMapper brandMapper;
    private final CarMapper carMapper;


    private final UserService userService;
    private final AuditService auditService;

    public ModelServiceImpl(ModelRepository modelRepository,
                            BrandRepository brandRepository, ModelMapper modelMapper,
                            ReporterService reporterService,
                            BrandMapper brandMapper,
                            CarMapper carMapper, UserService userService,
                            AuditService auditService) {
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
        this.reporterService = reporterService;
        this.brandMapper = brandMapper;
        this.carMapper = carMapper;
        this.userService = userService;
        this.auditService = auditService;
    }


    @Override
    public ResponseWrapper<Boolean> registerModel(ModelRegisterRequest modelRegisterRequest
            , String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<BrandEntity> optionalBrandEntity = brandRepository.findById(modelRegisterRequest.getBrandId());
            if (!optionalBrandEntity.isPresent()) {
                throw new NotFoundException("This Brand is not Exits !");
            }

            ModelEntity modelEntityRecord = ModelEntity.builder()
                    .name(modelRegisterRequest.getName())
                    .year(modelRegisterRequest.getYear())
                    .active(true)
                    .brand(optionalBrandEntity.get())
                    .build();
            modelRepository.save(modelEntityRecord);


            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.INSERT)
                            .description("Adding new Model entity " + modelEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Model Created Success !");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }

    @Override
    public ResponseWrapper<Boolean> updateModel(ModelDTO modelDTO, String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<ModelEntity> optionalModelEntity = modelRepository.findById(modelDTO.getId());
            if (!optionalModelEntity.isPresent()) {
                throw new NotFoundException("This Model is not Exits !");
            }
            ModelEntity modelEntityRecord = optionalModelEntity.get();
            if (!modelDTO.getName().equals(null) && !modelDTO.getName().equals(modelEntityRecord.getName())) {
                modelEntityRecord.setName(modelDTO.getName());
            }
            if (!modelDTO.getYear().equals(null) && !modelDTO.getYear().equals(modelEntityRecord.getYear())) {
                modelEntityRecord.setYear(modelDTO.getYear());
            }
            if (!modelDTO.getBrand().equals(null) &&
                    !modelDTO.getBrand().equals(brandMapper.convertToDTO(modelEntityRecord.getBrand()))) {
                modelEntityRecord.setBrand(brandMapper.convertToEntity(modelDTO.getBrand()));
            }
            modelRepository.save(modelEntityRecord);


            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.UPDATE)
                            .description("Updating new Model entity " + modelEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Model Updated Success !");


        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }

    @Override
    public ResponseWrapper<Boolean> deleteModel(Long modelId, String authHeader) {

        String username = auditService.getUsernameForAudit(authHeader);

        try {
            Optional<ModelEntity> optionalModelEntity = modelRepository.findById(modelId);
            if (!optionalModelEntity.isPresent()) {
                throw new NotFoundException("This Model is not Exits !");
            }
            ModelEntity modelEntityRecord = optionalModelEntity.get();

            modelEntityRecord.setActive(false);


            modelRepository.save(modelEntityRecord);

            AuditDTO auditDTO =
                    AuditDTO.builder()
                            .user(userService.getUserByUsername(username))
                            .action(AuditActions.DELETE)
                            .description("Deleting new Model entity " + modelEntityRecord.toString())
                            .timestamp(LocalDateTime.now())
                            .build();

            auditService.logAudit(auditDTO);

            return reporterService.reportSuccess("Model Deleted Success !");


        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<ModelDTO>> listAllModels(boolean active) {
        try {
            List<ModelDTO> modelList = new ArrayList<>();
            modelRepository.findAllByActive(active).forEach(model -> {
                modelList.add(modelMapper.convertToDTO(model));
            });
            return reporterService.reportSuccess(modelList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }

    @Override
    public ResponseWrapper<List<ModelDTO>> listModelsForBrand(boolean active, long BrandId) {
            try {
                Optional<BrandEntity> optionalBrandEntity = brandRepository.findById(BrandId);
                if (!optionalBrandEntity.isPresent()) {
                    throw new NotFoundException("This Brand doesn't not Exits !");
                }
                List<ModelDTO> modelList = new ArrayList<>();
                modelRepository.findAllByActiveAndBrand(active , optionalBrandEntity.get()).forEach(model -> {
                    modelList.add(modelMapper.convertToDTO(model));
                });
                return reporterService.reportSuccess(modelList);
            } catch (Exception e) {
                return reporterService.reportError(e);
            }

        }


    @Override
    public ResponseWrapper<List<CarDTO>> listCarsForModel(boolean active, long modelId){

        try{
            List<CarDTO> carList = new ArrayList<>();
            Optional<ModelEntity> optionalModelEntity = modelRepository.findById(modelId);
            if(!optionalModelEntity.isPresent()){
                throw new NotFoundException("Model entity not found");
            }
            optionalModelEntity.get().getCars().forEach(car ->
                carList.add(carMapper.convertToDTO(car))
            );
            return reporterService.reportSuccess(carList);
        }
        catch(Exception e){
            return reporterService.reportError(e);
        }
    }
}
