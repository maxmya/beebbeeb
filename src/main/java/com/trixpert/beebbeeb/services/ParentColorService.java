package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.ParentColorRegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.BrandDTO;
import com.trixpert.beebbeeb.data.to.ParentColorDTO;

import java.util.List;

public interface ParentColorService {

    ResponseWrapper<Boolean> AddParentColor(ParentColorRegistrationRequest parentColorRegistrationRequest
                , String authHeader);

    ResponseWrapper<Boolean> deleteParentColor(long parentColorId , String authHeader);

    ResponseWrapper<Boolean> updateParentColor(ParentColorDTO parentColorDTO , String authHeader);

    ResponseWrapper<List<ParentColorDTO>> getAllParentColors(boolean active);

    ResponseWrapper<ParentColorDTO> getParentColor(long parentColorId);
}
