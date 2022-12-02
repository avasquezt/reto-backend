package com.reto_backend.reto_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto_backend.reto_backend.dto.TestDTO;
import com.reto_backend.reto_backend.service.TestService;

@RestController 
@RequestMapping("/tests")
public class TestController {
    
    @Autowired
    TestService testService;

    @GetMapping
    public ResponseEntity<List<TestDTO>>  getList(){
        return ResponseEntity.ok(testService.getTests());
    }

    @GetMapping(value="{testId}")
    public ResponseEntity<TestDTO> getById(@PathVariable("affiliateId") Long testId){
        return ResponseEntity.ok(testService.getTestById(testId));
    }

}
