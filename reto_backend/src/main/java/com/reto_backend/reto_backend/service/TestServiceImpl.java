package com.reto_backend.reto_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reto_backend.reto_backend.dto.TestDTO;
import com.reto_backend.reto_backend.model.Test;
import com.reto_backend.reto_backend.repository.TestRepository;

@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TestDTO> getTests() {
        return testRepository.findAll()
        .stream()
        .map(this::convertEntityToDto)
        .collect(Collectors.toList());
    }

    @Override
    public TestDTO getTestById(Long id) {
        return convertEntityToDto(testRepository.findById(id).get());
    }

    @Override
    public TestDTO createTest(TestDTO testDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    private TestDTO convertEntityToDto(Test test){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
                TestDTO testDTO = new TestDTO();
        testDTO = modelMapper.map(test, TestDTO.class);
        return testDTO;
    }

    private Test convertDtoToEntity(TestDTO affiliateDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Test test = new Test();
        test = modelMapper.map(affiliateDTO, Test.class);
        return test;
    }
    
}
