package com.reto_backend.reto_backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reto_backend.reto_backend.dto.TestDTO;
import com.reto_backend.reto_backend.error.DataValidationException;
import com.reto_backend.reto_backend.service.TestService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(TestController.class)
public class TestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TestService testService;
    
    @Test
    void testCreateSuccess() throws Exception{
        TestDTO test = new TestDTO(null, "Math", "math test");

        TestDTO response = new TestDTO(Long.valueOf(1), "Math", "math test");

        Mockito.when(testService.createTest(any(TestDTO.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(test);

        mvc.perform(post("/tests")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.['Test created'].id", is(1)))
        .andExpect(jsonPath("$.['Test created'].name", is("Math")));
    }

    @Test
    void testCreateError() throws Exception{
        TestDTO test = new TestDTO(null, "Math", "math test");

        Mockito.when(testService.createTest(any(TestDTO.class))).thenThrow(DataValidationException.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(test);

        mvc.perform(post("/tests")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNotFound() throws Exception{
        Mockito.when(testService.deleteTest(anyLong())).thenReturn(false);

        mvc.perform(delete("/tests/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteSuccess() throws Exception{
        Mockito.when(testService.deleteTest(anyLong())).thenReturn(true);

        mvc.perform(delete("/tests/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    void testGetByIdExists() throws Exception{
        TestDTO test = new TestDTO(Long.valueOf(1), "Math", "math test");

        Mockito.when(testService.getTestById(anyLong())).thenReturn(test);

        mvc.perform(get("/tests/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Math")));
    }

    @Test
    void testGetByIdNotElements() throws Exception{
        Mockito.when(testService.getTestById(anyLong())).thenReturn(null);

        mvc.perform(get("/tests/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void testGetListWithElements() throws Exception{
        List<TestDTO> tests = new ArrayList<TestDTO>();
        tests.add(new TestDTO(Long.valueOf(1), "Math", "math test"));

        Mockito.when(testService.getTests()).thenReturn(tests);

        mvc.perform(get("/tests")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].name", is("Math")));
    }

    @Test
    void testGetListWithNoElements() throws Exception{
        List<TestDTO> tests = new ArrayList<TestDTO>();

        Mockito.when(testService.getTests()).thenReturn(tests);

        mvc.perform(get("/tests")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateError() throws Exception{
        Long id = Long.valueOf(1);
        TestDTO test = new TestDTO(null, "Math", "math test");

        Mockito.when(testService.updateTest(anyLong(), any(TestDTO.class))).thenThrow(DataValidationException.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(test);

        mvc.perform(put("/tests/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateSuccess() throws Exception{
        Long id = Long.valueOf(1);
        TestDTO test = new TestDTO(null, "Math", "math test");

        TestDTO response = new TestDTO(id, "Math", "math test");

        Mockito.when(testService.updateTest(anyLong(), any(TestDTO.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(test);

        mvc.perform(put("/tests/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.['Test updated'].id", is(1)))
        .andExpect(jsonPath("$.['Test updated'].name", is("Math")));
    }
}
