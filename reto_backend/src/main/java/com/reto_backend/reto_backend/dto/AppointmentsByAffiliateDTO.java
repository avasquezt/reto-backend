package com.reto_backend.reto_backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentsByAffiliateDTO {
    private Long affiliateid;
    private List<AppointmentDTO> appointments;
}
