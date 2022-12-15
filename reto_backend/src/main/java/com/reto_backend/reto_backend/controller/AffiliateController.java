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

import com.reto_backend.reto_backend.dto.AffiliateDTO;
import com.reto_backend.reto_backend.service.AffiliateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController 
@RequestMapping("/affiliates")
@Tag(name="Affiliates", description = "Manage affiliates")
public class AffiliateController {
    
    @Autowired
    AffiliateService affiliateService;

    @GetMapping
    @Operation(summary = "List affiliates",description = "Returns a list of affiliates")
    public ResponseEntity<List<AffiliateDTO>> getList(){
        List<AffiliateDTO> response = affiliateService.getAffiliates();
        if(response.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping(value="{affiliateId}")
    @Operation(summary = "Affiliate details",description = "Returns the details of a specific affiliate")
    public ResponseEntity<AffiliateDTO> getById(@Parameter(description = "Numeric id of the affiliate") @PathVariable(name="affiliateId",  required=false) Long affiliateId){
        AffiliateDTO response =  affiliateService.getAffiliateById(affiliateId);
        if(response != null ){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }

    @PostMapping
    @Operation(summary = "Create affiliate",description = "Add a new affiliate to the application")
    public ResponseEntity<?> create(@RequestBody AffiliateDTO affiliateDto){
        Map<String, Object> response = new HashMap<>();
        try {
            affiliateDto =  affiliateService.createAffiliate(affiliateDto);
            response.put("Affiliate created", affiliateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("Error:", e.getMessage());
            response.put("Date",  java.time.LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping(value="{affiliateId}")
    @Operation(summary = "Update affiliate",description = "Edit an existing affiliate")
    public ResponseEntity<?> update(@Parameter(description = "Numeric id of the affiliate to edit") @PathVariable(name="affiliateId") Long affiliateId, @RequestBody AffiliateDTO affiliateDto
        ){
        Map<String, Object> response = new HashMap<>();
        try {
            affiliateDto =  affiliateService.updateAffiliate(affiliateId, affiliateDto);
            response.put("Affiliate updated", affiliateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("Error:", e.getMessage());
            response.put("Date",  java.time.LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping(value="{affiliateId}")
    @Operation(summary = "Delete affiliate",description = "Remove an affiliate from the application")
    public ResponseEntity<String> delete(@Parameter(description = "Numeric id of the affiliate to delete") @PathVariable("affiliateId") Long affiliateId){
        boolean result = affiliateService.deleteAffiliate(affiliateId);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
        }
    }
}
