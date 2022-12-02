package com.reto_backend.reto_backend.dto;

import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private Long id;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hour;
    private Long idTest;
    private Long idAffiliate;
}
