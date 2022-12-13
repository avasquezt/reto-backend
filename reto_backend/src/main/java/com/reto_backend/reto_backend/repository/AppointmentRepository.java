package com.reto_backend.reto_backend.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reto_backend.reto_backend.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    List<Appointment> findAllByAffiliateId(Long idAffiliate);
    List<Appointment> findAllByDateOrderByAffiliateAsc(Date date);
}
