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
import com.reto_backend.reto_backend.dto.AffiliateDTO;
import com.reto_backend.reto_backend.error.DataValidationException;
import com.reto_backend.reto_backend.service.AffiliateService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(AffiliateController.class)
public class AffiliateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AffiliateService affiliateService;

    @Test
    void testCreateSuccess() throws Exception{
        AffiliateDTO affiliate = new AffiliateDTO(null, "Juan Perez", 20, "juanperez@mail.com");

        AffiliateDTO response = new AffiliateDTO(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");

        Mockito.when(affiliateService.createAffiliate(any(AffiliateDTO.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(affiliate);

        mvc.perform(post("/affiliates")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.['Affiliate created'].id", is(1)))
        .andExpect(jsonPath("$.['Affiliate created'].name", is("Juan Perez")));
    }

    @Test
    void testCreateError() throws Exception{
        AffiliateDTO affiliate = new AffiliateDTO(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");

        Mockito.when(affiliateService.createAffiliate(any(AffiliateDTO.class))).thenThrow(DataValidationException.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(affiliate);

        mvc.perform(post("/affiliates")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNotFound() throws Exception{
        Mockito.when(affiliateService.deleteAffiliate(anyLong())).thenReturn(false);

        mvc.perform(delete("/affiliates/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteSuccess() throws Exception{
        Mockito.when(affiliateService.deleteAffiliate(anyLong())).thenReturn(true);

        mvc.perform(delete("/affiliates/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    @Test
    void testGetByIdExists() throws Exception{
        AffiliateDTO affiliate = new AffiliateDTO(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");

        Mockito.when(affiliateService.getAffiliateById(anyLong())).thenReturn(affiliate);

        mvc.perform(get("/affiliates/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Juan Perez")));
    }

    @Test
    void testGetByIdNoElements() throws Exception{
        Mockito.when(affiliateService.getAffiliateById(anyLong())).thenReturn(null);

        mvc.perform(get("/affiliates/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    void testGetListWithElements() throws Exception {
        List<AffiliateDTO> affiliates = new ArrayList<AffiliateDTO>();
        affiliates.add(new AffiliateDTO(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com"));

        Mockito.when(affiliateService.getAffiliates()).thenReturn(affiliates);

        mvc.perform(get("/affiliates")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].name", is("Juan Perez")));
    }

    @Test
    void testGetListWithNoElements() throws Exception {
        List<AffiliateDTO> affiliates = new ArrayList<AffiliateDTO>();

        Mockito.when(affiliateService.getAffiliates()).thenReturn(affiliates);

        mvc.perform(get("/affiliates")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateError() throws Exception{
        Long id = Long.valueOf(1);
        AffiliateDTO affiliate = new AffiliateDTO(null, "Juan Perez", 20, "juanperez@mail.com");

        Mockito.when(affiliateService.updateAffiliate(anyLong(), any(AffiliateDTO.class))).thenThrow(DataValidationException.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(affiliate);

        mvc.perform(put("/affiliates/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateSuccess() throws Exception{
        Long id = Long.valueOf(1);
        AffiliateDTO affiliate = new AffiliateDTO(null, "Juan Perez", 20, "jp@mail.com");

        AffiliateDTO response = new AffiliateDTO(id, "Juan Perez", 20, "jp@mail.com");

        Mockito.when(affiliateService.updateAffiliate(anyLong(), any(AffiliateDTO.class))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(affiliate);

        mvc.perform(put("/affiliates/" + id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.['Affiliate updated'].id", is(1)))
        .andExpect(jsonPath("$.['Affiliate updated'].name", is("Juan Perez")));
    }

}
