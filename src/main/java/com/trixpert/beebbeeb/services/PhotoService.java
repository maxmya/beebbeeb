package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.PhotoRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.PhotoDTO;
import org.springframework.stereotype.Service;


@Service
public interface PhotoService {

    ResponseWrapper<Boolean> registerPhoto (PhotoRegistrationRequest photoRegistrationRequest
            , String authHeader);

    ResponseWrapper<Boolean> deletePhoto(long photoId, String authHeader);

    ResponseWrapper<Boolean> updatePhoto(PhotoRegistrationRequest photoRegistrationRequest,
                                        long photoId, String authHeader) ;

    ResponseWrapper<PhotoDTO> getPhoto(long photoId);


}
