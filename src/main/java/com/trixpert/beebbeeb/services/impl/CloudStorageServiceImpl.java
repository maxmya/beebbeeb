package com.trixpert.beebbeeb.services.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.trixpert.beebbeeb.services.CloudStorageService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class CloudStorageServiceImpl implements CloudStorageService {

    private final Storage storage;

    public CloudStorageServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String uploadFile(String directory, String fileName, File file) {
        try {
            BlobId fileBlobId = BlobId.of(directory, fileName);
            BlobInfo fileBlobInfo = BlobInfo.newBuilder(fileBlobId).build();
            byte[] fileBytes = Files.readAllBytes(Paths.get(file.toURI()));
            Blob blob = storage.create(fileBlobInfo, fileBytes);
            return blob.getMediaLink();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "cannot-upload-file";
        }
    }
}
