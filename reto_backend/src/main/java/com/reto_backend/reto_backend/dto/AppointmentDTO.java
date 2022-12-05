package com.reto_backend.reto_backend.dto;

import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    private Long id;

    @NotNull(message = "Date is mandatory")
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date date;

    @NotNull(message = "Hour is mandatory")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime hour;

    @NotNull(message = "idTest is mandatory")
    private Long idTest;

    @NotNull(message = "idAffiliate is mandatory")
    private Long idAffiliate;
}
