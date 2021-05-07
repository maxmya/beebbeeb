package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;
import com.trixpert.beebbeeb.services.SKUService;
import org.springframework.stereotype.Service;

@Service
public class SKUServiceImpl implements SKUService {

    @Override
    public String generateCarSKU(CarInstanceEntity carInstanceEntity, String serial) {

        String vendorCode = carInstanceEntity.getVendor().getName()
                .toUpperCase().substring(0, 3);

        String modelCode = carInstanceEntity.getCar().getModel().getEnglishName()
                .toUpperCase().substring(0, 3);

        String categoryCode = carInstanceEntity.getCar().getCategory().getName()
                .toUpperCase().substring(0, 3);

        String colorCode = carInstanceEntity.getCar().getColor().getName()
                .toUpperCase().substring(0, 3);

        String typeCode = carInstanceEntity.getCar().getCategory().getType().getName()
                .toUpperCase().substring(0, 3);

        String carInstanceId = String.valueOf(carInstanceEntity.getId());

        return vendorCode + modelCode + categoryCode + colorCode + typeCode + carInstanceId + serial;
    }
}
