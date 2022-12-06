package com.reto_backend.reto_backend.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<List<AppointmentDTO>> getList(){
        List<AppointmentDTO> response = appointmentService.getAppointments();
        if(response.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    
    @GetMapping(params = "date")
    public ResponseEntity<Iterable<AppointmentDTO>> getByDate(@RequestParam("date") @DateTimeFormat(pattern = "dd/MM/yyyy") Date date){
        List<AppointmentDTO> response = appointmentService.getAppointmentsByDate(date);
        if(response.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping(params = "idAffiliate")
    public ResponseEntity<Iterable<AppointmentDTO>> getByDate(@RequestParam("date") Long idAffiliate){
        List<AppointmentDTO> response = appointmentService.getAppointmentsByAffiliate(idAffiliate);
        if(response.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping(value="{appointmentId}")
    public ResponseEntity<AppointmentDTO> getById(@PathVariable("appointmentId") Long appointmentId){
        AppointmentDTO response =  appointmentService.getAppointmentById(appointmentId);
        if(response != null ){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AppointmentDTO appointmentDTO){
        Map<String, Object> response = new HashMap<>();
        try {
            appointmentDTO =  appointmentService.createAppointment(appointmentDTO);
            response.put("Appointment created", appointmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("Error:", e.getMessage());
            response.put("Date",  java.time.LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping(value="{appointmentId}")
    public ResponseEntity<?> update(@PathVariable("appointmentId") Long appointmentId, @RequestBody AppointmentDTO appointmentDTO){
        Map<String, Object> response = new HashMap<>();
        try {
            appointmentDTO =  appointmentService.updateAppointment(appointmentId, appointmentDTO);
            response.put("Appointment updated", appointmentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("Error:", e.getMessage());
            response.put("Date",  java.time.LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping(value="{appointmentId}")
    public ResponseEntity<String> delete(@PathVariable("appointmentId") Long appointmentId){
        boolean result = appointmentService.deleteAppointment(appointmentId);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
        }
    }
}
