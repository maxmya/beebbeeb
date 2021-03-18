package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.ColorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.CarDTO;
import com.trixpert.beebbeeb.data.to.ColorDTO;
import com.trixpert.beebbeeb.services.ColorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = {"Color API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/colors")
public class ColorController {

    private final ColorService colorService;

    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }


    @GetMapping("/list/active")
    @ApiOperation("Get all active Color List")
    public ResponseEntity<ResponseWrapper<List<ColorDTO>>> getAllColors() {
        return ResponseEntity.ok(colorService.getAllColors(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get all in active  color List")
    public ResponseEntity<ResponseWrapper<List<ColorDTO>>> getAllInActiveColors() {
        return ResponseEntity.ok(colorService.getAllColors(false));
    }

    @PutMapping("/update/{colorId}")
    @ApiOperation("Update an existing color with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> updateColor(@RequestPart(name = "body") ColorRegistrationRequest colorRegistrationRequest,
                                @PathVariable("colorId") long colorId, HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(colorService.updateColor(colorRegistrationRequest, colorId,authorizationHeader));
    }

    @PostMapping("/add")
    @ApiOperation("Add New  Color")
    public ResponseEntity<ResponseWrapper<Boolean>> addColor(@RequestBody ColorRegistrationRequest ColorRegistrationRequest,
                                                             HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(colorService.registerColor(ColorRegistrationRequest, authorizationHeader));
    }

    @PutMapping("/delete/{colorId}")
    @ApiOperation("Remove  Color By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteColor(@PathVariable("colorId") Long colorId,
                                                                HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(colorService.deleteColor(colorId, authorizationHeader));
    }

    @GetMapping("/cars/list/active/{colorId}")
    @ApiOperation("list of active cars for specific color")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> listActiveCarsForColor(
            @PathVariable("colorId") long colorId) {
        return ResponseEntity.ok(colorService.listCarsForColor(true, colorId));
    }

    @GetMapping("/cars/list/inactive/{colorId}")
    @ApiOperation("list of inactive cars for specific color")
    public ResponseEntity<ResponseWrapper<List<CarDTO>>> listInactiveCarsForColor(
            @PathVariable("colorId") long colorId) {
        return ResponseEntity.ok(colorService.listCarsForColor(false, colorId));
    }

    @PutMapping("/Get/{colorId}")
    @ApiOperation("Get  Color By Id")
    public ResponseEntity<ResponseWrapper<ColorDTO>> getColor(
            @PathVariable("colorId") Long colorId) {

        return ResponseEntity.ok(colorService.getColor(colorId));
    }
}