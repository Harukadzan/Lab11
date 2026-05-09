package com.example.labka11.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MaratovaAruzhanAsyncService {

    private static final Logger log = LoggerFactory.getLogger(MaratovaAruzhanAsyncService.class);

    // Async метод 1 — имитация отправки email
    @Async
    public CompletableFuture<String> sendEmail(String productName) {
        log.info("Sending email for product: {}", productName);
        try {
            Thread.sleep(2000); // имитируем долгую задачу (2 секунды)
        } catch (InterruptedException e) {
            log.error("Email sending interrupted: {}", e.getMessage());
        }
        log.info("Email sent for product: {}", productName);
        return CompletableFuture.completedFuture("Email sent for: " + productName);
    }

    // Async метод 2 — имитация генерации отчёта
    @Async
    public CompletableFuture<String> generateReport(String reportType) {
        log.info("Generating report: {}", reportType);
        try {
            Thread.sleep(3000); // имитируем долгую задачу (3 секунды)
        } catch (InterruptedException e) {
            log.error("Report generation interrupted: {}", e.getMessage());
        }
        log.info("Report generated: {}", reportType);
        return CompletableFuture.completedFuture("Report ready: " + reportType);
    }

    // Async метод 3 — имитация обновления склада
    @Async
    public CompletableFuture<String> updateInventory(Long productId) {
        log.info("Updating inventory for product id: {}", productId);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Inventory update interrupted: {}", e.getMessage());
        }
        log.info("Inventory updated for product id: {}", productId);
        return CompletableFuture.completedFuture("Inventory updated for id: " + productId);
    }
}