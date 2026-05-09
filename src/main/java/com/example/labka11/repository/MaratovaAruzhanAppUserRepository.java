package com.example.labka11.repository;

import com.example.labka11.entity.MaratovaAruzhanAppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaratovaAruzhanAppUserRepository extends JpaRepository<MaratovaAruzhanAppUser, Long> {
    Optional<MaratovaAruzhanAppUser> findByUsername(String username);
}