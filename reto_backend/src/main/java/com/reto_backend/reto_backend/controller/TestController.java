package com.reto_backend.reto_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto_backend.reto_backend.dto.TestDTO;
import com.reto_backend.reto_backend.service.TestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController 
@RequestMapping("/tests")
@Tag(name="Tests", description = "Manage tests")
public class TestController {
    
    @Autowired
    TestService testService;

    @GetMapping
    @Operation(summary = "List tests",description = "Returns a list of all tests")
    public ResponseEntity<List<TestDTO>> getList(){
        List<TestDTO> response = testService.getTests();
        if(response.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping(value="{testId}")
    @Operation(summary = "Test details",description = "Returns the details of a specific test")
    public ResponseEntity<TestDTO> getById(@Parameter(description = "Numeric id of the test")@PathVariable("testId") Long testId){
        TestDTO response =  testService.getTestById(testId);
        if(response != null ){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }

    @PostMapping
    @Operation(summary = "Create test",description = "Add a new test to the application")
    public ResponseEntity<?> create(@RequestBody TestDTO testDTO){
        Map<String, Object> response = new HashMap<>();
        try {
            testDTO =  testService.createTest(testDTO);
            response.put("Test created", testDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("Error:", e.getMessage());
            response.put("Date",  java.time.LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping(value="{testId}")
    @Operation(summary = "Update test",description = "Edit an existing test")
    public ResponseEntity<?> update(@Parameter(description = "Numeric id of the test to edit") @PathVariable("testId") Long testId, @RequestBody TestDTO testDTO){
        Map<String, Object> response = new HashMap<>();
        try {
            testDTO =  testService.updateTest(testId, testDTO);
            response.put("Test updated", testDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("Error:", e.getMessage());
            response.put("Date",  java.time.LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping(value="{testId}")
    @Operation(summary = "Delete test",description = "Remove a test from the application")
    public ResponseEntity<TestDTO> delete(@Parameter(description = "Numeric id of the test to delete") @PathVariable("testId") Long testId){
        boolean result = testService.deleteTest(testId);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
        }
    }
    
}
