package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.*;
import com.trixpert.beebbeeb.data.mappers.*;
import com.trixpert.beebbeeb.data.repositories.*;
import com.trixpert.beebbeeb.data.request.CarRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.CarService;
import com.trixpert.beebbeeb.services.ReporterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelRepository modelRepository;
    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;

    private final CarMapper carMapper;
    private final ModelMapper modelMapper;
    private final BranchMapper branchMapper;
    private final CategoryMapper categoryMapper;
    private final ColorMapper colorMapper;

    private final ReporterService reporterService;

    public CarServiceImpl(CarRepository carRepository,
                          ModelRepository modelRepository, BranchRepository branchRepository,
                          CategoryRepository categoryRepository, ColorRepository colorRepository,
                          CarMapper carMapper, ModelMapper modelMapper,
                          BranchMapper branchMapper, CategoryMapper categoryMapper,
                          ColorMapper colorMapper, ReporterService reporterService) {
        this.carRepository = carRepository;
        this.modelRepository = modelRepository;
        this.branchRepository = branchRepository;
        this.categoryRepository = categoryRepository;
        this.colorRepository = colorRepository;
        this.carMapper = carMapper;
        this.modelMapper = modelMapper;
        this.branchMapper = branchMapper;
        this.categoryMapper = categoryMapper;
        this.colorMapper = colorMapper;
        this.reporterService = reporterService;
    }


    @Override
    public ResponseWrapper<Boolean> registerCar(CarRegistrationRequest carRegistrationRequest) {
        try {
            Optional<ModelEntity> optionalModelEntity = modelRepository.findById(carRegistrationRequest.getModelId());
            Optional<BranchEntity> optionalBranchEntity = branchRepository.findById(carRegistrationRequest.getBranchId());
            Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(carRegistrationRequest.getCategoryId());
            Optional<ColorEntity> optionalColorEntity = colorRepository.findById(carRegistrationRequest.getColorId());

            if (!optionalModelEntity.isPresent()) {
                throw new NotFoundException("Model entity not found");
            }
            if (!optionalBranchEntity.isPresent()) {
                throw new NotFoundException("Branch entity not found");
            }
            if (!optionalCategoryEntity.isPresent()) {
                throw new NotFoundException("Category entity not found");
            }
            if (!optionalColorEntity.isPresent()) {
                throw new NotFoundException("Color entity not found");
            }
            ModelEntity modelRecord = optionalModelEntity.get();
            BranchEntity branchRecord = optionalBranchEntity.get();
            CategoryEntity categoryRecord = optionalCategoryEntity.get();
            ColorEntity colorRecord = optionalColorEntity.get();

            CarEntity carEntityRecord = CarEntity.builder()
                    .additionDate(carRegistrationRequest.getAdditionDate())
                    .model(modelRecord)
                    .branch(branchRecord)
                    .category(categoryRecord)
                    .color(colorRecord)
                    .active(true)
                    .build();

            carRepository.save(carEntityRecord);
            return reporterService.reportSuccess("A new Car has been added successfully");
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> updateCar(long carId, CarRegistrationRequest carRegistrationRequest) {
        try {
            Optional<CarEntity> optionalCarEntity = carRepository.findById(carId);
            if (!optionalCarEntity.isPresent()) {
                throw new NotFoundException("Car entity not found");
            }
            CarEntity carEntityRecord = optionalCarEntity.get();

            if (carRegistrationRequest.getAdditionDate() != null &&
                    !carRegistrationRequest.getAdditionDate().equals(carEntityRecord.getAdditionDate())) {
                carEntityRecord.setAdditionDate(carRegistrationRequest.getAdditionDate());
            }
            if (carRegistrationRequest.getModelId() != -1 &&
                    carRegistrationRequest.getModelId() != carEntityRecord.getModel().getId()) {
                Optional<ModelEntity> optionalModelEntity = modelRepository.findById(carRegistrationRequest.getModelId());
                if (!optionalModelEntity.isPresent()) {
                    throw new NotFoundException("Model entity not found");
                }
                carEntityRecord.setModel(optionalModelEntity.get());
            }

            if (carRegistrationRequest.getBranchId() != -1 &&
                    carRegistrationRequest.getBranchId() != carEntityRecord.getBranch().getId()) {
                Optional<BranchEntity> optionalBranchEntity = branchRepository.findById(carRegistrationRequest.getBranchId());
                if (!optionalBranchEntity.isPresent()) {
                    throw new NotFoundException("Branch entity not found");
                }
                carEntityRecord.setBranch(optionalBranchEntity.get());
            }

            if (carRegistrationRequest.getCategoryId() != -1 &&
                    carRegistrationRequest.getCategoryId() != carEntityRecord.getCategory().getId()) {
                Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(carRegistrationRequest.getCategoryId());
                if (!optionalCategoryEntity.isPresent()) {
                    throw new NotFoundException("Category entity not found");
                }
                carEntityRecord.setCategory(optionalCategoryEntity.get());
            }

            if (carRegistrationRequest.getColorId() != -1 &&
                    carRegistrationRequest.getColorId() != carEntityRecord.getColor().getId()) {
                Optional<ColorEntity> optionalColorEntity = colorRepository.findById(carRegistrationRequest.getColorId());
                if (!optionalColorEntity.isPresent()) {
                    throw new NotFoundException("Color entity not found");
                }
                carEntityRecord.setColor(optionalColorEntity.get());
            }
            carRepository.save(carEntityRecord);
            return reporterService.reportSuccess("Car with ID: ".concat(Long.toString(carId))
                    .concat(" has been updated successfully"));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Transactional
    @Override
    public ResponseWrapper<Boolean> deleteCar(long carId) {
        try {
            Optional<CarEntity> optionalCarEntity = carRepository.findById(carId);
            if (!optionalCarEntity.isPresent()) {
                throw new NotFoundException("Car entity not found");
            }
            CarEntity carEntityRecord = optionalCarEntity.get();
            carEntityRecord.setActive(false);
            carRepository.save(carEntityRecord);
            return reporterService.reportSuccess("Car with Id: ".concat(Long.toString(carId))
                    .concat(" has been deleted successfully"));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<CarDTO>> getAllCars(boolean active) {
        try {
            List<CarDTO> carDTOList = new ArrayList<>();
            carRepository.findAllByActive(active).forEach(car ->
                    carDTOList.add(carMapper.convertToDTO(car))
            );
            return reporterService.reportSuccess(carDTOList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<CarDTO> getCar(long carId) {
        try {
            Optional<CarEntity> optionalCarEntity = carRepository.findById(carId);
            if(!optionalCarEntity.isPresent()){
                throw new NotFoundException("This car not found");
            }
            CarEntity carEntityRecord = optionalCarEntity.get();
            return reporterService.reportSuccess(carMapper.convertToDTO(carEntityRecord));

        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }
}
