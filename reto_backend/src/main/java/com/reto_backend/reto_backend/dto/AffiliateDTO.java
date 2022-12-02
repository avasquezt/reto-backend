package com.reto_backend.reto_backend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AffiliateDTO {
    
    private Long id;
    private String name;
    private int age;
    private String mail;
    @JsonIgnore
    private List<AppointmentDTO> appointments;
}
