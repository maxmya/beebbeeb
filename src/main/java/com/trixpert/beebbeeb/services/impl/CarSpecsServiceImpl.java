package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.entites.EssentialSpecsEntity;
import com.trixpert.beebbeeb.data.entites.ExtraSpecsEntity;
import com.trixpert.beebbeeb.data.mappers.SpecsMapper;
import com.trixpert.beebbeeb.data.repositories.CarRepository;
import com.trixpert.beebbeeb.data.repositories.EssentialCarSpecsRepository;
import com.trixpert.beebbeeb.data.repositories.ExtraCarSpecsRepository;
import com.trixpert.beebbeeb.data.request.CarSpecsDeleteRequest;
import com.trixpert.beebbeeb.data.request.CarSpecsRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.SpecsDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.CarSpecsService;
import com.trixpert.beebbeeb.services.ReporterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarSpecsServiceImpl implements CarSpecsService {

    private final EssentialCarSpecsRepository essentialCarSpecsRepository;
    private final ExtraCarSpecsRepository extraCarSpecsRepository;
    private final CarRepository carRepository;

    private final SpecsMapper specsMapper;

    private final ReporterService reporterService;

    public CarSpecsServiceImpl(
            EssentialCarSpecsRepository essentialCarSpecsRepository,
            ExtraCarSpecsRepository extraCarSpecsRepository,
            CarRepository carRepository,
            SpecsMapper specsMapper,
            ReporterService reporterService
    ) {

        this.essentialCarSpecsRepository = essentialCarSpecsRepository;
        this.extraCarSpecsRepository = extraCarSpecsRepository;
        this.carRepository = carRepository;

        this.specsMapper = specsMapper;
        this.reporterService = reporterService;
    }

    @Override
    public ResponseWrapper<List<SpecsDTO>> getEssentialCarSpecs(long carId) {
        try {
            List<SpecsDTO> specsList = new ArrayList<>();
            Optional<CarEntity> carRecord = carRepository.findById(carId);
            if (!carRecord.isPresent()) {
                throw new NotFoundException("Car not found !");
            }
            List<EssentialSpecsEntity> specsEntities = essentialCarSpecsRepository
                    .findAllByCarAndActive(carRecord.get(), true);

            specsEntities.forEach(specsEntity -> {
                specsList.add(specsMapper.convertToDTO(specsEntity));
            });

            return reporterService.reportSuccess(specsList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<List<SpecsDTO>> getExtraCarSpecs(long carId) {
        try {
            List<SpecsDTO> specsList = new ArrayList<>();
            Optional<CarEntity> carRecord = carRepository.findById(carId);
            if (!carRecord.isPresent()) {
                throw new NotFoundException("Car not found !");
            }
            List<ExtraSpecsEntity> specsEntities = extraCarSpecsRepository
                    .findAllByCarAndActive(carRecord.get(), true);

            specsEntities.forEach(specsEntity -> {
                if (specsEntity.isActive()) {
                    specsList.add(specsMapper.convertToDTO(specsEntity));
                }
            });

            return reporterService.reportSuccess(specsList);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> addCarSpecs(CarSpecsRequest specsRequest) {
        try {
            Optional<CarEntity> carEntity = carRepository.findById(specsRequest.getCarId());

            if (!carEntity.isPresent()) {
                throw new NotFoundException("car not found !");
            }

            if (specsRequest.isEssential()) {

                EssentialSpecsEntity specsEntity = EssentialSpecsEntity
                        .builder()
                        .key(specsRequest.getKey())
                        .car(carEntity.get())
                        .value(specsRequest.getValue())
                        .active(true)
                        .build();

                essentialCarSpecsRepository.save(specsEntity);

            } else {

                ExtraSpecsEntity extraSpecsEntity = ExtraSpecsEntity
                        .builder()
                        .key(specsRequest.getKey())
                        .car(carEntity.get())
                        .value(specsRequest.getValue())
                        .active(true)
                        .build();

                extraCarSpecsRepository.save(extraSpecsEntity);
            }

            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<Boolean> deleteCarSpecs(CarSpecsDeleteRequest specsRequest) {
        try {
            if (specsRequest.isEssential()) {
                Optional<EssentialSpecsEntity> essentialSpecsEntity = essentialCarSpecsRepository.findById(specsRequest.getSpecsId());
                if (!essentialSpecsEntity.isPresent()) {
                    throw new NotFoundException("Specs Not Found  !!");
                }
                EssentialSpecsEntity specsEntity = essentialSpecsEntity.get();
                specsEntity.setActive(false);
                essentialCarSpecsRepository.save(specsEntity);
            } else {
                Optional<ExtraSpecsEntity> extraSpecsEntity = extraCarSpecsRepository.findById(specsRequest.getSpecsId());
                if (!extraSpecsEntity.isPresent()) {
                    throw new NotFoundException("Specs Not Found !!");
                }
                ExtraSpecsEntity specsEntity = extraSpecsEntity.get();
                specsEntity.setActive(false);
                extraCarSpecsRepository.save(specsEntity);
            }
            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
