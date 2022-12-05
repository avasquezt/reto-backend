package com.reto_backend.reto_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TestDTO {
    
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;
}
