package com.reto_backend.reto_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<AffiliateDTO>>  getList(){
        return ResponseEntity.ok(affiliateService.getAffiliates());
    }

    @GetMapping(value="{affiliateId}")
    public ResponseEntity<AffiliateDTO> getById(@PathVariable("affiliateId") Long affiliateId){
        return ResponseEntity.ok(affiliateService.getAffiliateById(affiliateId));
    }
}
