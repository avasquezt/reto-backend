package com.reto_backend.reto_backend.service;

import java.util.List;

import com.reto_backend.reto_backend.dto.TestDTO;

public interface TestService {

    TestDTO createTest(TestDTO testDTO);
    boolean deleteTest(Long id);
    List<TestDTO> getTests();
    TestDTO getTestById(Long id);
    TestDTO updateTest(Long id, TestDTO testDTO);
    
}
