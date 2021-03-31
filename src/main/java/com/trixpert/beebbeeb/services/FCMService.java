package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.request.FCMRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;

public interface FCMService {

    ResponseWrapper<Boolean> sendNotification(FCMRequest request);

}
