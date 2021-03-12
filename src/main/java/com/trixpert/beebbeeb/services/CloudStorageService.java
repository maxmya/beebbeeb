package com.trixpert.beebbeeb.services;


import java.io.File;

public interface CloudStorageService {

    String uploadFile(String directory, String fileName, File file);

}
