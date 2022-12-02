package com.reto_backend.reto_backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.reto_backend.reto_backend.model.Test;


@Repository
public interface TestRepository extends CrudRepository<Test, Long>{
    
}
