package com.reto_backend.reto_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AffiliateDTO {
    
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    
    private Integer age;

    @NotBlank(message = "Mail is mandatory")
    @Email(message = "Mail should be valid")
    private String mail;
}
