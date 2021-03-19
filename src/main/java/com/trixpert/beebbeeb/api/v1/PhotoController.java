package com.trixpert.beebbeeb.api.v1;


import com.trixpert.beebbeeb.data.request.PhotoRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.PhotoDTO;
import com.trixpert.beebbeeb.services.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = {"Photo API's"})
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RestController
@RequestMapping("/api/v1/photos")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }


    @PutMapping( "/update/{photoId}")
    @ApiOperation("Update an existing photo with new data")
    public  ResponseEntity<ResponseWrapper<Boolean>> updatePhoto(
            @Valid @RequestBody PhotoRegistrationRequest photoRegistrationRequest,
            @PathVariable("photoId") long photoId
            , HttpServletRequest request)  {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(photoService.updatePhoto(photoRegistrationRequest ,
                photoId ,authorizationHeader));
    }

    @PutMapping( "/add/")
    @ApiOperation("Update an existing photo with new data")
    public ResponseEntity<ResponseWrapper<Boolean>> addPhoto(
            @Valid @RequestBody PhotoRegistrationRequest photoRegistrationRequest,
            HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(photoService.registerPhoto(
                photoRegistrationRequest, authorizationHeader));
    }

    @PutMapping("/delete/{photoId}")
    @ApiOperation("Remove photo By Id")
    public ResponseEntity<ResponseWrapper<Boolean>> deletePhoto(
            @PathVariable("photoId") long photoId
            , HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        return ResponseEntity.ok(photoService.deletePhoto(photoId, authorizationHeader));
    }

    @GetMapping("/get/{photoId}")
    @ApiOperation("Get photo by Id")
    public ResponseEntity<ResponseWrapper<PhotoDTO>> getphoto(
            @PathVariable("photoId")long photoId){
        return ResponseEntity.ok(photoService.getPhoto(photoId));
    }
}
