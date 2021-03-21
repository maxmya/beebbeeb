package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.SettingsDTO;
import com.trixpert.beebbeeb.services.SettingsService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Photo API's"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }
    
    @GetMapping
    public ResponseEntity<ResponseWrapper<SettingsDTO>> getSystemSettings() {
        return ResponseEntity.ok(settingsService.getSystemSettings());
    }
}
