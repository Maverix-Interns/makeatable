package com.maverix.makeatable.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {

    private static final String UPLOAD_DIR = "./uploads/";

    public String storeFile(MultipartFile file) throws IOException {
        Path dir = Paths.get(UPLOAD_DIR);
        Files.createDirectories(dir);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = dir.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        return filePath.toAbsolutePath().toString();
    }
}
