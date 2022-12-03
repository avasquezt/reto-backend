package com.reto_backend.reto_backend.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reto_backend.reto_backend.dto.AppointmentDTO;
import com.reto_backend.reto_backend.service.AppointmentService;

@RestController 
@RequestMapping("/appointments")
public class AppointmentController {
    
    @Autowired
    AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>>  getList(){
        return ResponseEntity.ok(appointmentService.getAppointments());
    }

    @GetMapping(value="{appointmentId}")
    public ResponseEntity<AppointmentDTO> getById(@PathVariable("appointmentId") Long appointmentId){
        return ResponseEntity.ok(appointmentService.getAppointmentById(appointmentId));
    }

    @GetMapping(params = "date")
    public ResponseEntity<Iterable<AppointmentDTO>> getByDate (@RequestParam("date")@DateTimeFormat(pattern = "dd/MM/yyyy") Date date){
        return ResponseEntity.ok(appointmentService.getAppointmentsByDate(date));
    }
}
