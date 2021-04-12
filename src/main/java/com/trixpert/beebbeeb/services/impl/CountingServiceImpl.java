package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.CarEntity;
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
    public ResponseWrapper<FilterCountingResponse> countPure() {
        try {
            Map<String, Integer> typesCount = new HashMap<>();
            Map<String, Integer> modelCount = new HashMap<>();
            Map<String, Integer> brandCount = new HashMap<>();
            Map<String, Integer> colorCount = new HashMap<>();

            carInstanceRepository.findAllByActive(true).forEach(
                    carInstanceEntity -> {
                        CarEntity currentCar = carInstanceEntity.getCar();
                        increaseCountByOne(typesCount, currentCar.getCategory().getType().getName());
                        increaseCountByOne(modelCount, currentCar.getModel().getYear());
                        increaseCountByOne(brandCount, currentCar.getBrand().getName());
                        increaseCountByOne(colorCount, currentCar.getColor().getParentColor().getName());
                    }
            );

            List<CountingResponse> typesCountList = new ArrayList<>();
            typesCount.keySet().forEach(type -> typesCountList.add(new CountingResponse(type, typesCount.get(type))));

            List<CountingResponse> modelsCountList = new ArrayList<>();
            modelCount.keySet().forEach(model -> modelsCountList.add(new CountingResponse(model, modelCount.get(model))));

            List<CountingResponse> brandsCountList = new ArrayList<>();
            brandCount.keySet().forEach(brand -> brandsCountList.add(new CountingResponse(brand, brandCount.get(brand))));

            List<CountingResponse> colorsCountList = new ArrayList<>();
            colorCount.keySet().forEach(color -> colorsCountList.add(new CountingResponse(color, colorCount.get(color))));

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
