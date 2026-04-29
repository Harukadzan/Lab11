package com.example.labka11.entity;

import jakarta.persistence.*;

@Entity
public class Profile {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
