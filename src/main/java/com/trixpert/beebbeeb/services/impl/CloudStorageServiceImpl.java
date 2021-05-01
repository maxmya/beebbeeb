package com.trixpert.beebbeeb.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.trixpert.beebbeeb.services.CloudStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudStorageServiceImpl implements CloudStorageService {

    private final Cloudinary cloudinary;

    public CloudStorageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        Map result = cloudinary.uploader().uploadLarge(file.getBytes(), ObjectUtils.emptyMap());
        return (String) result.get("url");
    }

}
