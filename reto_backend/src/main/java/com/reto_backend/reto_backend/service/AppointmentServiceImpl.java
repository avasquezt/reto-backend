package com.reto_backend.reto_backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reto_backend.reto_backend.dto.AppointmentDTO;
import com.reto_backend.reto_backend.model.Appointment;
import com.reto_backend.reto_backend.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AppointmentDTO> getAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        return convertEntityToDto(appointmentRepository.findById(id).get());
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDate(Date date) {
        return appointmentRepository.findAllByDateOrderByAffiliateAsc(date).stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    

    private AppointmentDTO convertEntityToDto(Appointment appointment){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO = modelMapper.map(appointment, AppointmentDTO.class);
        return appointmentDTO;
    }

    private Appointment convertDtoToEntity(AppointmentDTO appointmentDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Appointment appointment = new Appointment();
        appointment = modelMapper.map(appointmentDTO, Appointment.class);
        return appointment;
    }
}
