package com.reto_backend.reto_backend.service;

import java.util.Date;
import java.util.List;

import com.reto_backend.reto_backend.dto.AppointmentDTO;
import com.reto_backend.reto_backend.dto.AppointmentsByAffiliateDTO;

public interface AppointmentService {
    List<AppointmentDTO> getAppointments();
    AppointmentDTO getAppointmentById();
    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);
    List<AppointmentsByAffiliateDTO> getByDate(Date date);
}
