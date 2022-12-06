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

@RestController 
@RequestMapping("/affiliates")
public class AffiliateController {
    
    @Autowired
    AffiliateService affiliateService;

    @GetMapping
    public ResponseEntity<List<AffiliateDTO>> getList(){
        List<AffiliateDTO> response = affiliateService.getAffiliates();
        if(response.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping(value="{affiliateId}")
    public ResponseEntity<AffiliateDTO> getById(@PathVariable("affiliateId") Long affiliateId){
        AffiliateDTO response =  affiliateService.getAffiliateById(affiliateId);
        if(response != null ){
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
        }
    }

    @PostMapping
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
    public ResponseEntity<?> update(@PathVariable("affiliateId") Long affiliateId, @RequestBody AffiliateDTO affiliateDto){
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
    public ResponseEntity<String> delete(@PathVariable("affiliateId") Long affiliateId){
        boolean result = affiliateService.deleteAffiliate(affiliateId);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
        }
    }
}
