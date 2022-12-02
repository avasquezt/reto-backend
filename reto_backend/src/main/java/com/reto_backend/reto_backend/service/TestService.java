package com.reto_backend.reto_backend.service;

import java.util.List;

import com.reto_backend.reto_backend.dto.TestDTO;

public interface TestService {
    List<TestDTO> getTests();
    TestDTO getTestById(Long id);
    TestDTO createTest(TestDTO testDTO);
}
