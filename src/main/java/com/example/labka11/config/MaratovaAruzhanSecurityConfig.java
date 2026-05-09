package com.example.labka11.config;

import com.example.labka11.repository.MaratovaAruzhanAppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class MaratovaAruzhanSecurityConfig {

    private final MaratovaAruzhanJwtUtil jwtUtil;
    private final MaratovaAruzhanAppUserRepository userRepo;

    public MaratovaAruzhanSecurityConfig(MaratovaAruzhanJwtUtil jwtUtil,
                                         MaratovaAruzhanAppUserRepository userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // шифрование паролей
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // отключаем csrf для REST API
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()           // регистрация/логин — открыты
                        .requestMatchers("/swagger-ui/**").permitAll()     // Swagger — открыт
                        .requestMatchers("/v3/api-docs/**").permitAll() // Swagger docs — открыт
                        .requestMatchers("/files/**").permitAll()
                        .anyRequest().authenticated()                      // всё остальное — только с токеном
                )
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain)
                    throws java.io.IOException, jakarta.servlet.ServletException {

                String header = request.getHeader("Authorization");

                if (header != null && header.startsWith("Bearer ")) {
                    String token = header.substring(7); // убираем "Bearer "

                    if (jwtUtil.isValid(token)) {
                        String username = jwtUtil.extractUsername(token);

                        userRepo.findByUsername(username).ifPresent(user -> {
                            var auth = new org.springframework.security.authentication
                                    .UsernamePasswordAuthenticationToken(
                                    username, null,
                                    java.util.List.of(new org.springframework.security.core
                                            .authority.SimpleGrantedAuthority(user.getRole()))
                            );
                            org.springframework.security.core.context.SecurityContextHolder
                                    .getContext().setAuthentication(auth);
                        });
                    }
                }
                chain.doFilter(request, response);
            }
        };
    }
}