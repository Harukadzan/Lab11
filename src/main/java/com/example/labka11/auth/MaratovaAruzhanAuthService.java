package com.example.labka11.auth;

import com.example.labka11.config.MaratovaAruzhanJwtUtil;
import com.example.labka11.entity.MaratovaAruzhanAppUser;
import com.example.labka11.repository.MaratovaAruzhanAppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MaratovaAruzhanAuthService {

    private final MaratovaAruzhanAppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MaratovaAruzhanJwtUtil jwtUtil;

    public MaratovaAruzhanAuthService(MaratovaAruzhanAppUserRepository userRepo,
                                      PasswordEncoder passwordEncoder,
                                      MaratovaAruzhanJwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(RegisterRequest req) {
        MaratovaAruzhanAppUser user = new MaratovaAruzhanAppUser();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword())); // шифруем пароль!
        user.setRole("ROLE_USER");
        userRepo.save(user);
        return jwtUtil.generateToken(req.getUsername());
    }

    public String login(LoginRequest req) {
        MaratovaAruzhanAppUser user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        return jwtUtil.generateToken(req.getUsername());
    }
}