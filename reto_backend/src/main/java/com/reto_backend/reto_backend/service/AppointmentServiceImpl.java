package com.reto_backend.reto_backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

import com.reto_backend.reto_backend.dto.AppointmentDTO;
import com.reto_backend.reto_backend.dto.AppointmentsByAffiliateDTO;
import com.reto_backend.reto_backend.model.Affiliate;
import com.reto_backend.reto_backend.model.Appointment;
import com.reto_backend.reto_backend.repository.AffiliateRepository;
import com.reto_backend.reto_backend.repository.AppointmentRepository;

public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AffiliateRepository affiliatesRepository;

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
    public List<AppointmentsByAffiliateDTO> getAppointmentsByDate(Date date) {

        // Query Affiliates matching the input date
        Optional<List<Affiliate>> appointmentsByAffiliates = Optional.ofNullable(affiliatesRepository.findAllByAppointmentsDate(date));

        // Variable to save results
        List<AppointmentsByAffiliateDTO> result = new ArrayList<AppointmentsByAffiliateDTO>();

        // Check if the query returned any results
        if (appointmentsByAffiliates.isPresent()) {

            // Loop through query results and build the answer
            for(Affiliate affiliate : appointmentsByAffiliates.get()){

                // Add element to answer
                result.add(new AppointmentsByAffiliateDTO(
                                // Add Affiliate ID
                                affiliate.getId(), 
                                // Add the Affiliate's tests that match the input date
                                affiliate.getAppointments().stream().filter(p -> p.getDate().equals(date))
                                // Convert Test Entity to DTO
                                    .map(this::convertEntityToDto).collect(Collectors.toList())
                            ));
            }
        }
        return result;
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
