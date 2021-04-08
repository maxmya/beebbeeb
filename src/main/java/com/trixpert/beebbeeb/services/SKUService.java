package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.entites.CarInstanceEntity;

public interface SKUService {
    // VENDOR MODEL CATEGORY COLOR TYPE INSTANCE_ID SERIAL
    // ### ### ### ### ### ### #
    String generateCarSKU(CarInstanceEntity carInstanceEntity,String serial);
}
