package com.example.labka11.controller;

import com.example.labka11.service.MaratovaAruzhanFileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class MaratovaAruzhanFileController {

    private final MaratovaAruzhanFileService fileService;

    public MaratovaAruzhanFileController(MaratovaAruzhanFileService fileService) {
        this.fileService = fileService;
    }

    // POST /files/upload — загрузить файл
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String filename = fileService.save(file);
            return ResponseEntity.ok("Файл загружен: " + filename);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка загрузки: " + e.getMessage());
        }
    }

    // GET /files/download/{filename} — скачать файл
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        try {
            Resource resource = fileService.load(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
}