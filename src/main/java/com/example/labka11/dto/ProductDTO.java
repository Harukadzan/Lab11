package com.example.labka11.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDTO {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Min(0)
    private double price;

}
