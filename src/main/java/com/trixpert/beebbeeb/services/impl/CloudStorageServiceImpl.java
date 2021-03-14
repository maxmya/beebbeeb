package com.trixpert.beebbeeb.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.trixpert.beebbeeb.services.CloudStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudStorageServiceImpl implements CloudStorageService {

    private final Cloudinary cloudinary;

    public CloudStorageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadFile(File file) throws IOException {
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        return (String) result.get("url");
    }

    @Override
    public File convertToFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.getOriginalFilename() != null) {
            File file = new File(multipartFile.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            return file;
        }
        return null;
    }
}
