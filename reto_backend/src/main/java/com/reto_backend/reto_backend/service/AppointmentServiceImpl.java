package com.reto_backend.reto_backend.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reto_backend.reto_backend.dto.AppointmentDTO;
import com.reto_backend.reto_backend.error.DataValidationException;
import com.reto_backend.reto_backend.model.Appointment;
import com.reto_backend.reto_backend.repository.AffiliateRepository;
import com.reto_backend.reto_backend.repository.AppointmentRepository;
import com.reto_backend.reto_backend.repository.TestRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AffiliateRepository affiliateRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Validator validator;

    AppointmentServiceImpl(Validator validator){
        this.validator = validator;
    }

    // Create a new Appointment
    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) throws DataValidationException{

        // Validate data
        this.validateAppointment(appointmentDTO);

        // Create Appointment entity and save it to DB
        Appointment appointment = convertDtoToEntity(appointmentDTO);
        appointment.setId(null);
        AppointmentDTO response = convertEntityToDto(appointmentRepository.save(appointment));

        return response;

    }

    // Get all Appointments
    @Override
    public List<AppointmentDTO> getAppointments() {

        return appointmentRepository.findAll()
                                    .stream()
                                    .map(this::convertEntityToDto)
                                    .collect(Collectors.toList());

    }

    // Get one Appointment
    @Override
    public AppointmentDTO getAppointmentById(Long id) {

        // Get the appointment with the input id
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        // If there are not results, return null
        if(appointment.isEmpty()){
            return null;
        }

        //if there are results, return the appointment
        return convertEntityToDto(appointment.get());

    }

    // Get Appointment by date, grouped / sorted by affiliate
    @Override
    public List<AppointmentDTO> getAppointmentsByDate(Date date) {

        return appointmentRepository.findAllByDateOrderByAffiliateAsc(date)
                                    .stream()
                                    .map(this::convertEntityToDto)
                                    .collect(Collectors.toList());

    }

    // Get Appointment by affiliate
    @Override
    public List<AppointmentDTO> getAppointmentsByAffiliate(Long affiliateId) {

        return appointmentRepository.findAllByAffiliate(affiliateId)
                                    .stream()
                                    .map(this::convertEntityToDto)
                                    .collect(Collectors.toList());

    }
    
    // Delete one Appointment
    @Override
    public boolean deleteAppointment(Long id) {

        // Check if the appointment exists
        boolean exists = appointmentRepository.existsById(id);
        if(!exists){
            return false;
        }

        // If the appointment exists, delete it
        appointmentRepository.deleteById(id);

        // Check if the appointment still exists (if the deletion failed);
        exists = appointmentRepository.existsById(id);
        if(exists){
            return false;
        }
        return true;

    }

    // Update one Appointment
    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) throws DataValidationException{
        
        boolean exists = appointmentRepository.existsById(id);

        // If the id in the request body is different to URL parameter, and if the appointment exists
        if(appointmentDTO.getId() != null && id != appointmentDTO.getId()){
            String errorMessage = "Appointment id cannot be changed";
            throw new DataValidationException(errorMessage);
        }else if(!exists){
            String errorMessage = "Appointment doesn't exist";
            throw new DataValidationException(errorMessage);
        }

        // Create the new object and update the data
        AppointmentDTO updatedAppointmentDTO = convertEntityToDto(appointmentRepository.findById(id).get());
        updatedAppointmentDTO.setDate(Objects.nonNull(appointmentDTO.getDate()) ? appointmentDTO.getDate() : updatedAppointmentDTO.getDate());
        updatedAppointmentDTO.setHour(Objects.nonNull(appointmentDTO.getHour()) ? appointmentDTO.getHour() : updatedAppointmentDTO.getHour());
        updatedAppointmentDTO.setIdAffiliate(Objects.nonNull(appointmentDTO.getIdAffiliate()) ? appointmentDTO.getIdAffiliate() : updatedAppointmentDTO.getIdAffiliate());
        updatedAppointmentDTO.setIdTest(Objects.nonNull(appointmentDTO.getIdTest()) ? appointmentDTO.getIdTest() : updatedAppointmentDTO.getIdTest());

        // Check if the new data is valid
        this.validateAppointment(updatedAppointmentDTO);

        // Create appointment entity and save it to DB
        Appointment appointment = convertDtoToEntity(updatedAppointmentDTO);
        AppointmentDTO response = convertEntityToDto(appointmentRepository.save(appointment));

        return response;

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

    private void validateAppointment(AppointmentDTO appointmentDTO) throws DataValidationException{

        // Validate DTO fields
        Set<ConstraintViolation<AppointmentDTO>> violations = validator.validate(appointmentDTO);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                                            .map(e -> e.getMessageTemplate())
                                            .collect(Collectors.joining(", "));
            throw new DataValidationException(errorMessage);
        }

        // Validate Affiliate id and Test id
        boolean exists = affiliateRepository.existsById(appointmentDTO.getIdAffiliate());
        if(!exists){
            String errorMessage = "Invalid Affiliate Id";
            throw new DataValidationException(errorMessage);
        }
        exists = testRepository.existsById(appointmentDTO.getIdTest());
        if(!exists){
            String errorMessage = "Invalid Test Id";
            throw new DataValidationException(errorMessage);
        }

    }

    public void setMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

}
