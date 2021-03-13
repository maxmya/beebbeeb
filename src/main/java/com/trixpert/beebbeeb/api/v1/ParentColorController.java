package com.trixpert.beebbeeb.api.v1;

import com.trixpert.beebbeeb.data.request.ParentColorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.ParentColorDTO;
import com.trixpert.beebbeeb.services.ParentColorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = {"ParentColor API"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/parentcolor")
public class ParentColorController {

    private final ParentColorService parentColorService;

    public ParentColorController(ParentColorService parentColorService) {
        this.parentColorService = parentColorService;
    }


    @GetMapping("/list/active")
    @ApiOperation("Get all active ParentColor List")
    public ResponseEntity<ResponseWrapper<List<ParentColorDTO>>> getAllParentColors() {
        return ResponseEntity.ok(parentColorService.getAllParentColors(true));
    }

    @GetMapping("/list/inactive")
    @ApiOperation("Get all in active parent color List")
    public ResponseEntity<ResponseWrapper<List<ParentColorDTO>>> getAllInActiveParentColors() {
        return ResponseEntity.ok(parentColorService.getAllParentColors(false));
    }

    @PutMapping("/update")
    @ApiOperation("Update an existing color with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> updateParentColor(
            @RequestPart(name = "body")ParentColorDTO parentColorDTO
            , HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(parentColorService.updateParentColor(parentColorDTO , authorizationHeader));
    }

    @PostMapping("/add")
    @ApiOperation("Add New Parent Color")
    public ResponseEntity<ResponseWrapper<Boolean>> addParentColor(
            @Valid @RequestBody ParentColorRegistrationRequest parentColorRegistrationRequest
            , HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(parentColorService.AddParentColor(parentColorRegistrationRequest
                , authorizationHeader));
    }

    @PutMapping("/delete/{parentColorId}")
    @ApiOperation("Remove Parent Color By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deleteParentColor(
            @PathVariable("parentColorId") Long parentcolorId
            , HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(parentColorService.deleteParentColor(parentcolorId, authorizationHeader));
    }
}