package com.reto_backend.reto_backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="Test")
public class TestDTO {
    
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;
}
