package com.reto_backend.reto_backend.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reto_backend.reto_backend.dto.TestDTO;
import com.reto_backend.reto_backend.error.DataValidationException;
import com.reto_backend.reto_backend.model.Test;
import com.reto_backend.reto_backend.repository.TestRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Validator validator;

    TestServiceImpl(Validator validator){
        this.validator = validator;
    }

    // Create one test
    @Override
    public TestDTO createTest(TestDTO testDTO) throws DataValidationException{

        // Validate data
        this.validateTest(testDTO);

        // Create test entity and save it to DB
        Test test = convertDtoToEntity(testDTO);;
        test.setId(null);
        TestDTO response = convertEntityToDto(testRepository.save(test));

        return response;
    }

    // Get all tests
    @Override
    public List<TestDTO> getTests() {

        return testRepository.findAll()
                                .stream()
                                .map(this::convertEntityToDto)
                                .collect(Collectors.toList());

    }

    // Get one test
    @Override
    public TestDTO getTestById(Long id) {

        // Get the test with the input id
        Optional<Test> test = testRepository.findById(id);

        // If there are not results, return null
        if(test.isEmpty()){
            return null;
        }

        //if there are results, return the test
        return convertEntityToDto(test.get());

    }

    // Delete one test
    @Override
    public boolean deleteTest(Long id) {

        // Check if the test exists
        boolean exists = testRepository.existsById(id);
        if(!exists){
            return false;
        }

        // If the test exists, delete it
        testRepository.deleteById(id);

        // Check if the test still exists (if the deletion failed);
        exists = testRepository.existsById(id);
        if(exists){
            return false;
        }
        return true;

    }

    // Update one test
    @Override
    public TestDTO updateTest(Long id, TestDTO testDTO) throws DataValidationException{

        boolean exists = testRepository.existsById(id);

        // If the id in the request body is different to URL parameter, and if the test exists
        if(testDTO.getId() != null && id != testDTO.getId()){
            String errorMessage = "Test id cannot be changed";
            throw new DataValidationException(errorMessage);
        }else if(!exists){
            String errorMessage = "Test doesn't exist";
            throw new DataValidationException(errorMessage);
        }

        // Create the new object and update the data
        TestDTO updatedTestDTO = convertEntityToDto(testRepository.findById(id).get());
        updatedTestDTO.setName(Objects.nonNull(testDTO.getName()) ? testDTO.getName() : updatedTestDTO.getName());
        updatedTestDTO.setDescription(Objects.nonNull(testDTO.getDescription()) ? testDTO.getDescription() : updatedTestDTO.getDescription());

        // Check if the new data is valid
        this.validateTest(updatedTestDTO);

        // Create affiliate entity and save it to DB
        Test test = convertDtoToEntity(updatedTestDTO);
        TestDTO response = convertEntityToDto(testRepository.save(test));

        return response;
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

    private void validateTest(TestDTO testDTO) throws DataValidationException{
        Set<ConstraintViolation<TestDTO>> violations = validator.validate(testDTO);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                                            .map(e -> e.getMessageTemplate())
                                            .collect(Collectors.joining(", "));
            throw new DataValidationException(errorMessage);
        }
    }
    
}
