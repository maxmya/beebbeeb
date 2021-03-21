package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.SettingsDTO;

public interface SettingsService {

    ResponseWrapper<SettingsDTO> getSystemSettings();

}
