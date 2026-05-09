package com.example.labka11.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;

@Service
public class MaratovaAruzhanFileService {

    private static final Logger log = LoggerFactory.getLogger(MaratovaAruzhanFileService.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    // сохранить файл на диск
    public String save(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        // создаём папку если не существует
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        log.info("File saved: {}", filename);
        return filename;
    }

    // загрузить файл с диска
    public Resource load(String filename) throws MalformedURLException {
        Path filePath = Paths.get(uploadDir).resolve(filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            log.error("File not found: {}", filename);
            throw new RuntimeException("Файл не найден: " + filename);
        }

        log.info("File loaded: {}", filename);
        return resource;
    }
}