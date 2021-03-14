package com.trixpert.beebbeeb.services;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface CloudStorageService {

    String uploadFile(File file) throws IOException;

    File convertToFile(MultipartFile multipartFile) throws IOException;

}
