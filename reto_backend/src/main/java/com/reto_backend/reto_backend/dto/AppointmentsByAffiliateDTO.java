package com.reto_backend.reto_backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class AppointmentsByAffiliateDTO {
    private Long affiliateid;
    private List<AppointmentDTO> appointments;
}
