package com.trixpert.beebbeeb.services;


import com.trixpert.beebbeeb.data.response.ResponseWrapper;

public interface ReporterService {

    <T> ResponseWrapper<T> reportError(Exception exception, T data);

    <T> ResponseWrapper<T> reportError(Exception exception);

    <T> ResponseWrapper<T> reportSuccess(T data);

    ResponseWrapper<Boolean> reportSuccess(String message);

    ResponseWrapper<Boolean> reportSuccess();

}