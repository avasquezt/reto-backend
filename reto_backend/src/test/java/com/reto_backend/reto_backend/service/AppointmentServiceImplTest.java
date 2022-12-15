package com.reto_backend.reto_backend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.reto_backend.reto_backend.dto.AppointmentDTO;
import com.reto_backend.reto_backend.error.DataValidationException;
import com.reto_backend.reto_backend.model.Affiliate;
import com.reto_backend.reto_backend.model.Appointment;
import com.reto_backend.reto_backend.model.Test;
import com.reto_backend.reto_backend.repository.AffiliateRepository;
import com.reto_backend.reto_backend.repository.AppointmentRepository;
import com.reto_backend.reto_backend.repository.TestRepository;

import jakarta.validation.Validation;

public class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepositoryMock = Mockito.mock(AppointmentRepository.class);

    @Mock
    private AffiliateRepository affiliateRepositoryMock = Mockito.mock(AffiliateRepository.class);

    @Mock
    private TestRepository testRepositoryMock = Mockito.mock(TestRepository.class);

    @InjectMocks
    AppointmentServiceImpl appointmentService;;

    @BeforeEach
    void setUp(){
        appointmentService= new AppointmentServiceImpl(Validation.buildDefaultValidatorFactory().getValidator());
        appointmentService.setMapper(new ModelMapper());
        MockitoAnnotations.openMocks(this);
    }

    @org.junit.jupiter.api.Test
    void testCreateAppointmentInvalidData() {

        // Null date
        AppointmentDTO appointmentDTONullDate = new AppointmentDTO(null, null, LocalTime.now(), Long.valueOf(1), Long.valueOf(1));
        Appointment appointment = new Appointment(Long.valueOf(1), new Date(), LocalTime.now(), new Test(), new Affiliate());
        Mockito.when(appointmentRepositoryMock.save(any(Appointment.class))).thenReturn(appointment);
        Assertions.assertThrowsExactly(DataValidationException.class, () -> appointmentService.createAppointment(appointmentDTONullDate));

        // Null hour
        AppointmentDTO appointmentDTONullHour = new AppointmentDTO(null, new Date(), null, Long.valueOf(1), Long.valueOf(1));
        Assertions.assertThrowsExactly(DataValidationException.class, () -> appointmentService.createAppointment(appointmentDTONullHour));

        // Null test id
        AppointmentDTO appointmentDTONullTestId = new AppointmentDTO(null, new Date(), LocalTime.now(), null, Long.valueOf(1));
        Assertions.assertThrowsExactly(DataValidationException.class, () -> appointmentService.createAppointment(appointmentDTONullTestId));

        // Null affiliate id
        AppointmentDTO appointmentDTONullAffiliateId = new AppointmentDTO(null, new Date(), LocalTime.now(), Long.valueOf(1), null);
        Assertions.assertThrowsExactly(DataValidationException.class, () -> appointmentService.createAppointment(appointmentDTONullAffiliateId));

        // invalid affiliate id
        AppointmentDTO appointmentDTOInvalidAffiliateId = new AppointmentDTO(null, new Date(), LocalTime.now(), Long.valueOf(1), Long.valueOf(2));
        Mockito.when(affiliateRepositoryMock.existsById(anyLong())).thenReturn(false);

        Assertions.assertThrowsExactly(DataValidationException.class, () -> appointmentService.createAppointment(appointmentDTOInvalidAffiliateId));

        // invalid test id
        AppointmentDTO appointmentDTOInvalidTestId = new AppointmentDTO(null, new Date(), LocalTime.now(), Long.valueOf(2), Long.valueOf(1));
        Mockito.when(affiliateRepositoryMock.existsById(anyLong())).thenReturn(true);
        Mockito.when(testRepositoryMock.existsById(anyLong())).thenReturn(false);
        
        Assertions.assertThrowsExactly(DataValidationException.class, () -> appointmentService.createAppointment(appointmentDTOInvalidTestId));

    }

    @org.junit.jupiter.api.Test
    void testCreateAppointmentSuccess() {

        // Valid Data
        Date date = new Date();
        LocalTime hour = LocalTime.now();

        AppointmentDTO appointmentDTO = new AppointmentDTO(null, date, hour, Long.valueOf(1), Long.valueOf(1));
        Appointment appointment = new Appointment(Long.valueOf(1), date, hour, new Test(), new Affiliate());
        appointment.getAffiliate().setId(Long.valueOf(1));
        appointment.getTest().setId(Long.valueOf(1));

        Mockito.when(appointmentRepositoryMock.save(any(Appointment.class))).thenReturn(appointment);
        Mockito.when(affiliateRepositoryMock.existsById(anyLong())).thenReturn(true);
        Mockito.when(testRepositoryMock.existsById(anyLong())).thenReturn(true);
        
        AppointmentDTO response = new AppointmentDTO(Long.valueOf(1),date, hour,Long.valueOf(1), Long.valueOf(1));
        Assertions.assertEquals(response, appointmentService.createAppointment(appointmentDTO));

    }

    @org.junit.jupiter.api.Test
    void testDeleteAppointmentNoData() {

        Mockito.when(appointmentRepositoryMock.existsById(anyLong())).thenReturn(false);
        Mockito.doNothing().when(appointmentRepositoryMock).deleteById(anyLong());
        boolean response = appointmentService.deleteAppointment(Long.valueOf(1));
        
        Assertions.assertEquals(false, response);
    }

    @org.junit.jupiter.api.Test
    void testDeleteAppointmentNoDeleted() {

        Mockito.when(appointmentRepositoryMock.existsById(anyLong())).thenReturn(true).thenReturn(true);
        Mockito.doNothing().when(appointmentRepositoryMock).deleteById(anyLong());
        boolean response = appointmentService.deleteAppointment(Long.valueOf(1));
        
        Assertions.assertEquals(false, response);
    }

    @org.junit.jupiter.api.Test
    void testDeleteAppointmentSuccess() {

        Mockito.when(appointmentRepositoryMock.existsById(anyLong())).thenReturn(true).thenReturn(false);
        Mockito.doNothing().when(appointmentRepositoryMock).deleteById(anyLong());
        boolean response = appointmentService.deleteAppointment(Long.valueOf(1));
        
        Assertions.assertEquals(true, response);
    }

    @org.junit.jupiter.api.Test
    void testGetAppointmentByIdSuccess() {

        Date date = new Date();
        LocalTime hour = LocalTime.now();

        Appointment appointment = new Appointment(Long.valueOf(1), date, hour, new Test(), new Affiliate());
        appointment.getAffiliate().setId(Long.valueOf(1));
        appointment.getTest().setId(Long.valueOf(1));
    
        Mockito.when(appointmentRepositoryMock.findById(anyLong())).thenReturn(Optional.of(appointment));
        AppointmentDTO response = appointmentService.getAppointmentById(Long.valueOf(1));

        AppointmentDTO expected = new AppointmentDTO(Long.valueOf(1),date, hour,Long.valueOf(1), Long.valueOf(1));

        Assertions.assertEquals(expected, response);

    }

    @org.junit.jupiter.api.Test
    void testGetAppointmentByIdNoData() {
    
        Mockito.when(appointmentRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        AppointmentDTO response = appointmentService.getAppointmentById(Long.valueOf(1));

        AppointmentDTO expected = null;

        Assertions.assertEquals(expected, response);

    }

    @org.junit.jupiter.api.Test
    void testGetAppointments() {

        Date date = new Date();
        LocalTime hour = LocalTime.now();

        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.add(new Appointment(Long.valueOf(1), date, hour, new Test(), new Affiliate()));
        appointments.get(0).getAffiliate().setId(Long.valueOf(1));
        appointments.get(0).getTest().setId(Long.valueOf(1));
        appointments.add(new Appointment(Long.valueOf(2), date, hour, new Test(), new Affiliate()));
        appointments.get(1).getAffiliate().setId(Long.valueOf(2));
        appointments.get(1).getTest().setId(Long.valueOf(2));
        appointments.add(new Appointment(Long.valueOf(3), date, hour, new Test(), new Affiliate()));
        appointments.get(2).getAffiliate().setId(Long.valueOf(3));
        appointments.get(2).getTest().setId(Long.valueOf(3));

        Mockito.when(appointmentRepositoryMock.findAll()).thenReturn(appointments);
        List<AppointmentDTO> result = appointmentService.getAppointments();

        List<AppointmentDTO> expected = new ArrayList<AppointmentDTO>();
        expected.add(new AppointmentDTO(Long.valueOf(1),date, hour,Long.valueOf(1), Long.valueOf(1)));
        expected.add(new AppointmentDTO(Long.valueOf(2),date, hour,Long.valueOf(2), Long.valueOf(2)));
        expected.add(new AppointmentDTO(Long.valueOf(3),date, hour,Long.valueOf(3), Long.valueOf(3)));

        Assertions.assertEquals(expected, result);
    }

    @org.junit.jupiter.api.Test
    void testGetAppointmentsByAffiliate() {
        Date date = new Date();
        LocalTime hour = LocalTime.now();

        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.add(new Appointment(Long.valueOf(2), date, hour, new Test(), new Affiliate()));
        appointments.get(0).getAffiliate().setId(Long.valueOf(2));
        appointments.get(0).getTest().setId(Long.valueOf(2));
        appointments.add(new Appointment(Long.valueOf(3), date, hour, new Test(), new Affiliate()));
        appointments.get(1).getAffiliate().setId(Long.valueOf(2));
        appointments.get(1).getTest().setId(Long.valueOf(3));

        Mockito.when(appointmentRepositoryMock.findAllByAffiliateId(anyLong())).thenReturn(appointments);
        List<AppointmentDTO> result = appointmentService.getAppointmentsByAffiliate(Long.valueOf(2));

        List<AppointmentDTO> expected = new ArrayList<AppointmentDTO>();
        expected.add(new AppointmentDTO(Long.valueOf(2),date, hour,Long.valueOf(2), Long.valueOf(2)));
        expected.add(new AppointmentDTO(Long.valueOf(3),date, hour,Long.valueOf(3), Long.valueOf(2)));

        Assertions.assertEquals(expected, result);
    }

    @org.junit.jupiter.api.Test
    void testGetAppointmentsByDate() {

        Date date = new Date();
        LocalTime hour = LocalTime.now();

        List<Appointment> appointments = new ArrayList<Appointment>();
        appointments.add(new Appointment(Long.valueOf(2), date, hour, new Test(), new Affiliate()));
        appointments.get(0).getAffiliate().setId(Long.valueOf(2));
        appointments.get(0).getTest().setId(Long.valueOf(2));
        appointments.add(new Appointment(Long.valueOf(3), date, hour, new Test(), new Affiliate()));
        appointments.get(1).getAffiliate().setId(Long.valueOf(2));
        appointments.get(1).getTest().setId(Long.valueOf(3));

        Mockito.when(appointmentRepositoryMock.findAllByDateOrderByAffiliateAsc(any(Date.class))).thenReturn(appointments);
        List<AppointmentDTO> result = appointmentService.getAppointmentsByDate(date);

        List<AppointmentDTO> expected = new ArrayList<AppointmentDTO>();
        expected.add(new AppointmentDTO(Long.valueOf(2),date, hour,Long.valueOf(2), Long.valueOf(2)));
        expected.add(new AppointmentDTO(Long.valueOf(3),date, hour,Long.valueOf(3), Long.valueOf(2)));

        Assertions.assertEquals(expected, result);
        
    }

    @org.junit.jupiter.api.Test
    void testUpdateAppointmentInvalidData() {

        Date date = new Date();
        LocalTime hour = LocalTime.now();

        // Invalid Id
        Long id = Long.valueOf(1);
        AppointmentDTO appointmentDTOInvalidId = new AppointmentDTO(Long.valueOf(2),date, hour,Long.valueOf(2), Long.valueOf(2));
        Appointment appointment = new Appointment(Long.valueOf(1), date, hour, new Test(), new Affiliate());
        appointment.getAffiliate().setId(Long.valueOf(1));
        appointment.getTest().setId(Long.valueOf(1));

        Mockito.when(appointmentRepositoryMock.save(any(Appointment.class))).thenReturn(appointment);
        Mockito.when(appointmentRepositoryMock.existsById(anyLong())).thenAnswer(i -> i.getArguments()[0] == id);

        Assertions.assertThrowsExactly(DataValidationException.class, () -> appointmentService.updateAppointment(id, appointmentDTOInvalidId));

    }

    @org.junit.jupiter.api.Test
    void testUpdateAppointmentNotExists() {

        Date date = new Date();
        LocalTime hour = LocalTime.now();

        // Id not found
        Long id = Long.valueOf(1);
        AppointmentDTO appointmentDTOInvalidId = new AppointmentDTO(null,date, hour,Long.valueOf(2), Long.valueOf(2));
        Appointment appointment = new Appointment(Long.valueOf(1), date, hour, new Test(), new Affiliate());
        appointment.getAffiliate().setId(Long.valueOf(1));
        appointment.getTest().setId(Long.valueOf(1));

        Mockito.when(appointmentRepositoryMock.save(any(Appointment.class))).thenReturn(appointment);
        Mockito.when(appointmentRepositoryMock.existsById(anyLong())).thenReturn(false);

        Assertions.assertThrowsExactly(DataValidationException.class, () -> appointmentService.updateAppointment(id, appointmentDTOInvalidId));

    }

    @org.junit.jupiter.api.Test
    void testUpdateAppointmentSuccess() {

        Date date = new Date();
        LocalTime hour = LocalTime.now();

        Long id = Long.valueOf(1);
        AppointmentDTO appointmentDTO = new AppointmentDTO(null,date, hour,Long.valueOf(2), Long.valueOf(2));
        Appointment appointment = new Appointment(Long.valueOf(1), date, hour, new Test(), new Affiliate());
        appointment.getAffiliate().setId(Long.valueOf(2));
        appointment.getTest().setId(Long.valueOf(2));

        Mockito.when(appointmentRepositoryMock.save(any(Appointment.class))).thenReturn(appointment);
        Mockito.when(appointmentRepositoryMock.findById(anyLong())).thenReturn(Optional.of(appointment));
        Mockito.when(appointmentRepositoryMock.existsById(anyLong())).thenReturn(true);
        Mockito.when(affiliateRepositoryMock.existsById(anyLong())).thenReturn(true);
        Mockito.when(testRepositoryMock.existsById(anyLong())).thenReturn(true);

        AppointmentDTO expected = new AppointmentDTO(Long.valueOf(1),date, hour,Long.valueOf(2), Long.valueOf(2));

        Assertions.assertEquals(expected, appointmentService.updateAppointment(id, appointmentDTO));

        // With null data
        AppointmentDTO appointmentDTONullData = new AppointmentDTO(id, null, null, null, null);

        Assertions.assertEquals(expected, appointmentService.updateAppointment(id, appointmentDTONullData));
    }

}
