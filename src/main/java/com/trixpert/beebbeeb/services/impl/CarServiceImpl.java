package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.*;
import com.trixpert.beebbeeb.data.mappers.*;
import com.trixpert.beebbeeb.data.repositories.*;
import com.trixpert.beebbeeb.data.request.CarRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.AuditService;
import com.trixpert.beebbeeb.services.CarService;
import com.trixpert.beebbeeb.services.ReporterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelRepository modelRepository;
    private final CategoryRepository categoryRepository;
    private final ColorRepository colorRepository;
    private final ParentColorRepository parentColorRepository;
    private final TypeRepository typeRepository;
    private final UserRepository userRepository;

    private final CarMapper carMapper;

    private final ReporterService reporterService;
    private final AuditService auditService;

    public CarServiceImpl(CarRepository carRepository,
                          ModelRepository modelRepository,
                          CategoryRepository categoryRepository,
                          ColorRepository colorRepository,
                          ParentColorRepository parentColorRepository,
                          TypeRepository typeRepository,
                          UserRepository userRepository, CarMapper carMapper,
                          ReporterService reporterService, AuditService auditService) {

        this.carRepository = carRepository;
        this.modelRepository = modelRepository;
        this.categoryRepository = categoryRepository;
        this.colorRepository = colorRepository;
        this.parentColorRepository = parentColorRepository;
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
        this.carMapper = carMapper;
        this.reporterService = reporterService;
        this.auditService = auditService;
    }


    @Override
    public ResponseWrapper<Boolean> registerCar(CarRegistrationRequest carRegistrationRequest, String authHeader) {
        try {

            String username = auditService.getUsernameForAudit(authHeader);

            Optional<UserEntity> optionalUser = userRepository.findByEmail(username);

            if (!optionalUser.isPresent()) {
                throw new NotFoundException("user not found !!");
            }

            Optional<ModelEntity> optionalModelEntity = modelRepository.findById(carRegistrationRequest.getModelId());

            if (!optionalModelEntity.isPresent()) {
                throw new NotFoundException("Model entity not found");
            }

            Optional<ParentColorEntity> parentColorOptional = parentColorRepository.findById(carRegistrationRequest.getParentColorId());

            if (!parentColorOptional.isPresent()) {
                throw new NotFoundException("Parent Color Not Found");
            }

            ColorEntity colorEntity = ColorEntity.builder()

                    .name(carRegistrationRequest.getColorName())
                    .code(carRegistrationRequest.getColorCode())
                    .build();
            colorEntity.setParentColor(parentColorOptional.get());
            colorRepository.save(colorEntity);

            Optional<TypeEntity> typeEntityOptional = typeRepository.findById(carRegistrationRequest.getTypeId());

            if (!typeEntityOptional.isPresent()) {
                throw new NotFoundException("Type Not Found");
            }

            CategoryEntity categoryEntity = CategoryEntity.builder()
                    .name(carRegistrationRequest.getCategoryName())
                    .build();
            categoryEntity.setType(typeEntityOptional.get());
            categoryRepository.save(categoryEntity);


            ModelEntity modelRecord = optionalModelEntity.get();

            CarEntity carEntityRecord = CarEntity.builder()
                    .additionDate(LocalDateTime.now())
                    .model(modelRecord)
                    .brand(modelRecord.getBrand())
                    .creator(optionalUser.get())
                    .category(categoryEntity)
                    .color(colorEntity)
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
            if (!optionalCarEntity.isPresent()) {
                throw new NotFoundException("This car not found");
            }
            CarEntity carEntityRecord = optionalCarEntity.get();
            return reporterService.reportSuccess(carMapper.convertToDTO(carEntityRecord));

        } catch (Exception e) {
            return reporterService.reportError(e);
        }

    }
}
