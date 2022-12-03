package com.reto_backend.reto_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reto_backend.reto_backend.model.Affiliate;

@Repository
public interface AffiliateRepository extends JpaRepository<Affiliate, Long>{
    
}