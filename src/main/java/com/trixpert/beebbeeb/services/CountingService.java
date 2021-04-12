package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.response.FilterCountingResponse;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;

public interface CountingService {

    ResponseWrapper<FilterCountingResponse> countPure();

}
