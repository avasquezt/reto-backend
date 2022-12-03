package com.reto_backend.reto_backend.service;

import java.util.Date;
import java.util.List;

import com.reto_backend.reto_backend.dto.AppointmentDTO;

public interface AppointmentService {
    List<AppointmentDTO> getAppointments();
    AppointmentDTO getAppointmentById(Long id);
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);
    List<AppointmentDTO> getAppointmentsByDate(Date date);
}
