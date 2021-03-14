package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.ColorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.ColorDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ColorService {

    ResponseWrapper<Boolean> registerColor(ColorRegistrationRequest colorRegistrationRequest
     , String authHeader) ;

    ResponseWrapper<Boolean> deleteColor(long colorId , String authHeader);

    ResponseWrapper<Boolean> updateColor(ColorRegistrationRequest colorRegistrationRequest,
                                         long colorId, String authHeader);

    ResponseWrapper<List<ColorDTO>> getAllColors(boolean active);

    ResponseWrapper<List<CarDTO>> listCarsForColor(boolean active, long colorId);

}
