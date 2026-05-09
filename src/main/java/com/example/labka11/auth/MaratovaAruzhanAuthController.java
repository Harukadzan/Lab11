package com.example.labka11.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class MaratovaAruzhanAuthController {

    private final MaratovaAruzhanAuthService authService;

    public MaratovaAruzhanAuthController(MaratovaAruzhanAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest req) {
        String token = authService.register(req);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest req) {
        String token = authService.login(req);
        return ResponseEntity.ok(token);
    }
}