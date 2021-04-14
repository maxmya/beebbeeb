package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.CarEntity;
import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.data.repositories.CarInstanceRepository;
import com.trixpert.beebbeeb.data.response.CountingResponse;
import com.trixpert.beebbeeb.data.response.FilterCountingResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.services.CountingService;
import com.trixpert.beebbeeb.services.ReporterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountingServiceImpl implements CountingService {

    private final ReporterService reporterService;
    private final CarInstanceRepository carInstanceRepository;

    public CountingServiceImpl(ReporterService reporterService,
                               CarInstanceRepository carInstanceRepository) {

        this.reporterService = reporterService;
        this.carInstanceRepository = carInstanceRepository;
    }

    @Override
    public ResponseWrapper<FilterCountingResponse> countPure(
            String type,
            String model,
            String brand,
            String color
    ) {
        try {
            Map<String, Integer> typesCount = new HashMap<>();
            Map<String, Integer> modelCount = new HashMap<>();
            Map<String, Integer> brandCount = new HashMap<>();
            Map<String, Integer> colorCount = new HashMap<>();

            for (CarInstanceEntity carInstanceEntity : carInstanceRepository.findAllByActive(true)) {
                CarEntity currentCar = carInstanceEntity.getCar();
                String carType = currentCar.getCategory().getType().getName();
                String carModel = currentCar.getModel().getYear();
                String carBrand = currentCar.getBrand().getName();
                String carColor = currentCar.getColor().getParentColor().getName();

                if (type != null) {
                    if (!carType.equals(type)) continue;
                }

                if (model != null) {
                    if (!carModel.equals(model)) continue;
                }

                if (brand != null) {
                    if (!carBrand.equals(brand)) continue;
                }

                if (color != null) {
                    if (!carColor.equals(color)) continue;
                }

                increaseCountByOne(typesCount, carType);
                increaseCountByOne(modelCount, carModel);
                increaseCountByOne(brandCount, carBrand);
                increaseCountByOne(colorCount, carColor);
            }

            List<CountingResponse> typesCountList = new ArrayList<>();
            typesCount.keySet().forEach(currentType -> typesCountList.add(new CountingResponse(currentType, typesCount.get(currentType))));

            List<CountingResponse> modelsCountList = new ArrayList<>();
            modelCount.keySet().forEach(currentModel -> modelsCountList.add(new CountingResponse(currentModel, modelCount.get(currentModel))));

            List<CountingResponse> brandsCountList = new ArrayList<>();
            brandCount.keySet().forEach(currentBrand -> brandsCountList.add(new CountingResponse(currentBrand, brandCount.get(currentBrand))));

            List<CountingResponse> colorsCountList = new ArrayList<>();
            colorCount.keySet().forEach(currentColor -> colorsCountList.add(new CountingResponse(currentColor, colorCount.get(currentColor))));

            return reporterService.reportSuccess(new FilterCountingResponse(
                    typesCountList, brandsCountList, modelsCountList, colorsCountList
            ));
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    private void increaseCountByOne(Map<String, Integer> countMap, String key) {
        if (countMap.containsKey(key)) {
            int oldCount = countMap.get(key);
            countMap.put(key, ++oldCount);
        } else {
            countMap.put(key, 1);
        }
    }

}
