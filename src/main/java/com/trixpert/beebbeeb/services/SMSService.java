package com.trixpert.beebbeeb.services;

import com.trixpert.beebbeeb.data.response.ResponseWrapper;

public interface SMSService {

    ResponseWrapper<Boolean> sendSMSMessage(String message, String phone);

}
