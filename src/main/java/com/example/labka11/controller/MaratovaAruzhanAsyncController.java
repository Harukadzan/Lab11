package com.example.labka11.controller;

import com.example.labka11.service.MaratovaAruzhanAsyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/async")
public class MaratovaAruzhanAsyncController {

    private final MaratovaAruzhanAsyncService asyncService;

    public MaratovaAruzhanAsyncController(MaratovaAruzhanAsyncService asyncService) {
        this.asyncService = asyncService;
    }

    // POST /async/email?productName=Laptop
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestParam String productName) {
        asyncService.sendEmail(productName); // запускаем в фоне
        return ResponseEntity.ok("Email задача запущена в фоне!");
        // ответ приходит СРАЗУ, не ждём 2 секунды
    }

    // POST /async/report?type=monthly
    @PostMapping("/report")
    public ResponseEntity<String> generateReport(@RequestParam String type) {
        asyncService.generateReport(type);
        return ResponseEntity.ok("Отчёт генерируется в фоне!");
    }

    // POST /async/inventory?productId=1
    @PostMapping("/inventory")
    public ResponseEntity<String> updateInventory(@RequestParam Long productId) {
        asyncService.updateInventory(productId);
        return ResponseEntity.ok("Обновление склада запущено в фоне!");
    }
}