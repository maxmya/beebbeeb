package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.TypeRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.TypeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface TypeService {

    ResponseWrapper<Boolean> addType(TypeRegistrationRequest typeRegistrationRequest, MultipartFile logoFile, String authHeader) throws IOException;

    ResponseWrapper<Boolean> deleteType(long typeId, String authHeader);

    ResponseWrapper<Boolean> updateType(TypeRegistrationRequest typeRegistrationRequest, long typeId, MultipartFile logoFile, String authHeader) throws IOException;

    ResponseWrapper<List<TypeDTO>> listAllTypes(boolean active);

    ResponseWrapper<TypeDTO> getType(long typeId);
}
