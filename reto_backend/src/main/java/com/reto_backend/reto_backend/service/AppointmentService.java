package com.reto_backend.reto_backend.service;

import java.util.Date;
import java.util.List;

import com.reto_backend.reto_backend.dto.AppointmentDTO;

public interface AppointmentService {

    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);
    boolean deleteAppointment(Long id);
    List<AppointmentDTO> getAppointments();
    AppointmentDTO getAppointmentById(Long id);
    List<AppointmentDTO> getAppointmentsByDate(Date date);
    List<AppointmentDTO> getAppointmentsByAffiliate(Long affiliateId);
    List<AppointmentDTO> getAppointmentsByAffiliateAndDate(Long affiliateId, Date date);
    AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO);
}
