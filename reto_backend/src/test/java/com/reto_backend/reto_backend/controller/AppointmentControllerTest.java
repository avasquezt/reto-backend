package com.reto_backend.reto_backend.controller;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reto_backend.reto_backend.dto.AppointmentDTO;
import com.reto_backend.reto_backend.error.DataValidationException;
import com.reto_backend.reto_backend.service.AppointmentService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AppointmentService appointmentService;

    @Test
    void testCreateSuccess() throws Exception{
        Date date = new Date();
        LocalTime hour = LocalTime.now();
        AppointmentDTO appointment = new AppointmentDTO(null, date, hour, Long.valueOf(1), Long.valueOf(1));

        AppointmentDTO response = new AppointmentDTO(Long.valueOf(1), date, null, Long.valueOf(1), Long.valueOf(1));

        Mockito.when(appointmentService.createAppointment(any(AppointmentDTO.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String body = objectMapper.writeValueAsString(appointment);

        mvc.perform(post("/appointments")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.['Appointment created'].id", is(1)))
        .andExpect(jsonPath("$.['Appointment created'].idTest", is(1)))
        .andExpect(jsonPath("$.['Appointment created'].idAffiliate", is(1)));
    }

    @Test
    void testCreateError() throws Exception{
        Date date = new Date();
        LocalTime hour = LocalTime.now();
        AppointmentDTO appointment = new AppointmentDTO(null, date, hour, Long.valueOf(1), Long.valueOf(1));

        Mockito.when(appointmentService.createAppointment(any(AppointmentDTO.class))).thenThrow(DataValidationException.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String body = objectMapper.writeValueAsString(appointment);

        mvc.perform(post("/appointments")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNotFound() throws Exception{
        Mockito.when(appointmentService.deleteAppointment(anyLong())).thenReturn(false);

        mvc.perform(delete("/appointments/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteSuccess() throws Exception{
        Mockito.when(appointmentService.deleteAppointment(anyLong())).thenReturn(true);

        mvc.perform(delete("/appointments/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    void testGetByDateWithElements() throws Exception{
        Date date = new Date();
        LocalTime hour = LocalTime.now();
        List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
        appointments.add(new AppointmentDTO(Long.valueOf(1), date, hour, Long.valueOf(1), Long.valueOf(1)));

        Mockito.when(appointmentService.getAppointmentsByDate(any(Date.class))).thenReturn(appointments);

        mvc.perform(get("/appointments")
        .param("date", "07/12/2022")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].idAffiliate", is(1)))
        .andExpect(jsonPath("$[0].idTest", is(1)));
    }

    @Test
    void testGetByDateWithNoElements() throws Exception{
        List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();

        Mockito.when(appointmentService.getAppointmentsByDate(any(Date.class))).thenReturn(appointments);

        mvc.perform(get("/appointments")
        .param("date", "07/12/2022")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }

    @Test
    void testGetByAffiliateIdWithElements() throws Exception{
        Date date = new Date();
        LocalTime hour = LocalTime.now();
        List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
        appointments.add(new AppointmentDTO(Long.valueOf(1), date, hour, Long.valueOf(1), Long.valueOf(1)));

        Mockito.when(appointmentService.getAppointmentsByAffiliate(anyLong())).thenReturn(appointments);

        mvc.perform(get("/appointments")
        .param("idAffiliate", "1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].idAffiliate", is(1)))
        .andExpect(jsonPath("$[0].idTest", is(1)));
    }

    @Test
    void testGetByAffiliateIdNoElements() throws Exception{
        List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();

        Mockito.when(appointmentService.getAppointmentsByAffiliate(anyLong())).thenReturn(appointments);

        mvc.perform(get("/appointments")
        .param("idAffiliate", "1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }

    @Test
    void testGetByIdExists() throws Exception{
        Date date = new Date();
        LocalTime hour = LocalTime.now();
        AppointmentDTO appointmentDTO = new AppointmentDTO(Long.valueOf(1), date, hour, Long.valueOf(1), Long.valueOf(1));

        Mockito.when(appointmentService.getAppointmentById(anyLong())).thenReturn(appointmentDTO);

        mvc.perform(get("/appointments/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.idTest", is(1)))
        .andExpect(jsonPath("$.idAffiliate", is(1)));
    }

    @Test
    void testGetByIdNoElements() throws Exception{
        Mockito.when(appointmentService.getAppointmentById(anyLong())).thenReturn(null);

        mvc.perform(get("/appointments/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void testGetListWithNoElements() throws Exception{
        List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();

        Mockito.when(appointmentService.getAppointments()).thenReturn(appointments);

        mvc.perform(get("/appointments")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }

    @Test
    void testGetListWithElements() throws Exception{
        Date date = new Date();
        LocalTime hour = LocalTime.now();
        List<AppointmentDTO> appointments = new ArrayList<AppointmentDTO>();
        appointments.add(new AppointmentDTO(Long.valueOf(1), date, hour, Long.valueOf(1), Long.valueOf(1)));

        Mockito.when(appointmentService.getAppointments()).thenReturn(appointments);

        mvc.perform(get("/appointments")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].idAffiliate", is(1)))
        .andExpect(jsonPath("$[0].idTest", is(1)));
    }

    @Test
    void testUpdateError() throws Exception{
        Date date = new Date();
        LocalTime hour = LocalTime.now();
        Long id = Long.valueOf(1);
        AppointmentDTO appointment = new AppointmentDTO(Long.valueOf(1), date, hour, Long.valueOf(1), Long.valueOf(1));

        Mockito.when(appointmentService.updateAppointment(anyLong(), any(AppointmentDTO.class))).thenThrow(DataValidationException.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String body = objectMapper.writeValueAsString(appointment);

        mvc.perform(put("/appointments/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateSuccess() throws Exception{
        Date date = new Date();
        LocalTime hour = LocalTime.now();
        Long id = Long.valueOf(1);
        AppointmentDTO appointment = new AppointmentDTO(null, date, hour, Long.valueOf(1), Long.valueOf(1));

        AppointmentDTO response = new AppointmentDTO(id, date, hour, Long.valueOf(1), Long.valueOf(1));

        Mockito.when(appointmentService.updateAppointment(anyLong(), any(AppointmentDTO.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        String body = objectMapper.writeValueAsString(appointment);

        mvc.perform(put("/appointments/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.['Appointment updated'].id", is(1)))
        .andExpect(jsonPath("$.['Appointment updated'].idTest", is(1)))
        .andExpect(jsonPath("$.['Appointment updated'].idAffiliate", is(1)));
    }
}
