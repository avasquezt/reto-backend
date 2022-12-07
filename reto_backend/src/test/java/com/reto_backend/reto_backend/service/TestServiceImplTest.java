package com.reto_backend.reto_backend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.reto_backend.reto_backend.dto.TestDTO;
import com.reto_backend.reto_backend.error.DataValidationException;
import com.reto_backend.reto_backend.model.Test;
import com.reto_backend.reto_backend.repository.TestRepository;

import jakarta.validation.Validation;

public class TestServiceImplTest {

    @Mock
    private TestRepository testRepositoryMock = Mockito.mock(TestRepository.class);

    @InjectMocks
    TestServiceImpl testService;

    @BeforeEach
    void setUp(){
        testService= new TestServiceImpl(Validation.buildDefaultValidatorFactory().getValidator());
        testService.setMapper(new ModelMapper());
        MockitoAnnotations.openMocks(this);
    }

    @org.junit.jupiter.api.Test
    void testCreateTestInvalidData() {

        // Null name
        TestDTO testDTONullName = new TestDTO(null, null, "math test");
        Test test = new Test(Long.valueOf(1), null, "math test");
        Mockito.when(testRepositoryMock.save(any(Test.class))).thenReturn(test);
        Assertions.assertThrowsExactly(DataValidationException.class, () -> testService.createTest(testDTONullName));

    }

    @org.junit.jupiter.api.Test
    void testCreateTestSuccess() {

        // Null name
        TestDTO testDTO = new TestDTO(null, "Math", "math test");
        Test test = new Test(Long.valueOf(1), "Math", "math test");
        Mockito.when(testRepositoryMock.save(any(Test.class))).thenReturn(test);

        TestDTO response = new TestDTO(Long.valueOf(1), "Math", "math test");
        Assertions.assertEquals(response, testService.createTest(testDTO));
        
    }

    @org.junit.jupiter.api.Test
    void testDeleteTestNoData() {

        Mockito.when(testRepositoryMock.existsById(anyLong())).thenReturn(false);
        Mockito.doNothing().when(testRepositoryMock).deleteById(anyLong());
        boolean response = testService.deleteTest(Long.valueOf(1));
        
        Assertions.assertEquals(false, response);

    }

    @org.junit.jupiter.api.Test
    void testDeleteTestNoDeleted() {

        Mockito.when(testRepositoryMock.existsById(anyLong())).thenReturn(true).thenReturn(true);
        Mockito.doNothing().when(testRepositoryMock).deleteById(anyLong());
        boolean response = testService.deleteTest(Long.valueOf(1));
        
        Assertions.assertEquals(false, response);

    }

    @org.junit.jupiter.api.Test
    void testDeleteTestSuccess() {

        Mockito.when(testRepositoryMock.existsById(anyLong())).thenReturn(true).thenReturn(false);
        Mockito.doNothing().when(testRepositoryMock).deleteById(anyLong());
        boolean response = testService.deleteTest(Long.valueOf(1));
        
        Assertions.assertEquals(true, response);

    }

    @org.junit.jupiter.api.Test
    void testGetTestByIdSuccess() {

        Test test = new Test(Long.valueOf(1), "Math", "math test");

        Mockito.when(testRepositoryMock.findById(anyLong())).thenReturn(Optional.of(test));
        TestDTO response = testService.getTestById(Long.valueOf(1));

        TestDTO expected = new TestDTO(Long.valueOf(1), "Math", "math test");

        Assertions.assertEquals(expected, response);

    }

    @org.junit.jupiter.api.Test
    void testGetTestByIdWithNoData() {

        Mockito.when(testRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        TestDTO response = testService.getTestById(Long.valueOf(1));

        TestDTO expected = null;

        Assertions.assertEquals(expected, response);
    }

    @org.junit.jupiter.api.Test
    void testGetTests() {

        List<Test> tests = new ArrayList<Test>();
        tests.add(new Test(Long.valueOf(1), "Math", "math test"));
        tests.add(new Test(Long.valueOf(2), "English", "english test"));
        tests.add(new Test(Long.valueOf(3), "Spanish", "spanish test"));

        Mockito.when(testRepositoryMock.findAll()).thenReturn(tests);
        List<TestDTO> result = testService.getTests();

        List<TestDTO> expected = new ArrayList<TestDTO>();
        expected.add(new TestDTO(Long.valueOf(1), "Math", "math test"));
        expected.add(new TestDTO(Long.valueOf(2), "English", "english test"));
        expected.add(new TestDTO(Long.valueOf(3), "Spanish", "spanish test"));

        Assertions.assertEquals(expected, result);

    }

    @org.junit.jupiter.api.Test
    void testUpdateTestInvalidData() {

        // Invalid Id
        Long id = Long.valueOf(1);
        TestDTO testDTOInvalidId = new TestDTO(Long.valueOf(2), "Math", "math test");
        Test test = new Test(Long.valueOf(1), "Math", "math test");

        Mockito.when(testRepositoryMock.save(any(Test.class))).thenReturn(test);
        Mockito.when(testRepositoryMock.existsById(anyLong())).thenAnswer(i -> i.getArguments()[0] == id);

        Assertions.assertThrowsExactly(DataValidationException.class, () -> testService.updateTest(id, testDTOInvalidId));
    }

    @org.junit.jupiter.api.Test
    void testUpdateTestNotExists() {

        // Id not found
        Long id = Long.valueOf(2);
        TestDTO testDTOInvalidId = new TestDTO(null, "Math", "math test");
        Test test = new Test(Long.valueOf(1), "Math", "math test");

        Mockito.when(testRepositoryMock.save(any(Test.class))).thenReturn(test);
        Mockito.when(testRepositoryMock.existsById(anyLong())).thenReturn(false);

        Assertions.assertThrowsExactly(DataValidationException.class, () -> testService.updateTest(id, testDTOInvalidId));
    }

    @org.junit.jupiter.api.Test
    void testUpdateTestSuccess() {

        // Valid with data
        Long id = Long.valueOf(1);
        TestDTO testDTO = new TestDTO(null, "Math", "math test");
        Test test = new Test(Long.valueOf(1), "Math", "math test");

        Mockito.when(testRepositoryMock.save(any(Test.class))).thenReturn(test);
        Mockito.when(testRepositoryMock.findById(anyLong())).thenReturn(Optional.of(test));
        Mockito.when(testRepositoryMock.existsById(anyLong())).thenAnswer(i -> i.getArguments()[0] == id);

        TestDTO expected = new TestDTO(Long.valueOf(1), "Math", "math test");

        Assertions.assertEquals(expected, testService.updateTest(id, testDTO));

        // With null data
        TestDTO testDTONullData = new TestDTO(id, null, null);

        Assertions.assertEquals(expected, testService.updateTest(id, testDTONullData));


    }
}
