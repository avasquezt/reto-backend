package com.reto_backend.reto_backend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.reto_backend.reto_backend.dto.AffiliateDTO;
import com.reto_backend.reto_backend.error.DataValidationException;
import com.reto_backend.reto_backend.model.Affiliate;
import com.reto_backend.reto_backend.repository.AffiliateRepository;

import jakarta.validation.Validation;

public class AffiliateServiceImplTest {

    @Mock
    private AffiliateRepository affiliateRepositoryMock = Mockito.mock(AffiliateRepository.class);

    @InjectMocks
    AffiliateServiceImpl affiliateService;

    @BeforeEach
    void setUp(){
        affiliateService= new AffiliateServiceImpl(Validation.buildDefaultValidatorFactory().getValidator());
        affiliateService.setMapper(new ModelMapper());
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAffiliateInvalidData() {

        // Null name
        AffiliateDTO affiliateDTONullName = new AffiliateDTO(null, null, 20, "juanperez@mail.com");
        Affiliate affiliate = new Affiliate(Long.valueOf(1), null, 20, "juanperez@mail.com");
        Mockito.when(affiliateRepositoryMock.save(any(Affiliate.class))).thenReturn(affiliate);
        Assertions.assertThrowsExactly(DataValidationException.class, () -> affiliateService.createAffiliate(affiliateDTONullName));

        // Null Email
        AffiliateDTO affiliateDTONullEmail = new AffiliateDTO(null, "Juan Perez", 20, null);
        Assertions.assertThrowsExactly(DataValidationException.class, () -> affiliateService.createAffiliate(affiliateDTONullEmail));

        // Invalid Email
        AffiliateDTO affiliateDTOInvalidEmail = new AffiliateDTO(null, "Juan Perez", 20, "juanperezmail.com");
        Assertions.assertThrowsExactly(DataValidationException.class, () -> affiliateService.createAffiliate(affiliateDTOInvalidEmail));

    }

    @Test
    void testCreateAffiliateSuccess() {

        AffiliateDTO affiliateDTO = new AffiliateDTO(null, "Juan Perez", 20, "juanperez@mail.com");
        Affiliate affiliate = new Affiliate(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");

        Mockito.when(affiliateRepositoryMock.save(any(Affiliate.class))).thenReturn(affiliate);

        AffiliateDTO response = new AffiliateDTO(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");
        Assertions.assertEquals(response, affiliateService.createAffiliate(affiliateDTO));

    }

    @Test
    void testDeleteAffiliateNoData() {

        Mockito.when(affiliateRepositoryMock.existsById(anyLong())).thenReturn(false);
        Mockito.doNothing().when(affiliateRepositoryMock).deleteById(anyLong());
        boolean response = affiliateService.deleteAffiliate(Long.valueOf(1));
        
        Assertions.assertEquals(false, response);
    }

    @Test
    void testDeleteAffiliateNoDeleted() {

        Mockito.when(affiliateRepositoryMock.existsById(anyLong())).thenReturn(true).thenReturn(true);
        Mockito.doNothing().when(affiliateRepositoryMock).deleteById(anyLong());
        boolean response = affiliateService.deleteAffiliate(Long.valueOf(1));
        
        Assertions.assertEquals(false, response);
    }

    @Test
    void testDeleteAffiliateSuccess() {

        Mockito.when(affiliateRepositoryMock.existsById(anyLong())).thenReturn(true).thenReturn(false);
        Mockito.doNothing().when(affiliateRepositoryMock).deleteById(anyLong());
        boolean response = affiliateService.deleteAffiliate(Long.valueOf(1));
        
        Assertions.assertEquals(true, response);
    }

    @Test
    void testGetAffiliateByIdSuccess() {

        Affiliate affiliate = new Affiliate(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");

        Mockito.when(affiliateRepositoryMock.findById(anyLong())).thenReturn(Optional.of(affiliate));
        AffiliateDTO response = affiliateService.getAffiliateById(Long.valueOf(1));

        AffiliateDTO expected = new AffiliateDTO(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");

        Assertions.assertEquals(expected, response);
    }

    @Test
    void testGetAffiliateByIdWithNoData() {

        Mockito.when(affiliateRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        AffiliateDTO response = affiliateService.getAffiliateById(Long.valueOf(1));

        AffiliateDTO expected = null;

        Assertions.assertEquals(expected, response);
    }

    @Test
    void testGetAffiliates() {
        List<Affiliate> affiliates = new ArrayList<Affiliate>();
        affiliates.add(new Affiliate(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com"));
        affiliates.add(new Affiliate(Long.valueOf(2), "Jose Gomez", 23, "josegomez@mail.com"));
        affiliates.add(new Affiliate(Long.valueOf(3), "Ana Mejia", 28, "anamejia@mail.com"));

        Mockito.when(affiliateRepositoryMock.findAll()).thenReturn(affiliates);
        List<AffiliateDTO> result = affiliateService.getAffiliates();

        List<AffiliateDTO> expected = new ArrayList<AffiliateDTO>();
        expected.add(new AffiliateDTO(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com"));
        expected.add(new AffiliateDTO(Long.valueOf(2), "Jose Gomez", 23, "josegomez@mail.com"));
        expected.add(new AffiliateDTO(Long.valueOf(3), "Ana Mejia", 28, "anamejia@mail.com"));

        Assertions.assertEquals(expected, result);
    }

    @Test
    void testUpdateAffiliateInvalidData() {

        // Invalid Id
        Long id = Long.valueOf(1);
        AffiliateDTO affiliateDTOInvalidId = new AffiliateDTO(Long.valueOf(2), "Juan Perez", 20, "juanperez@mail.com");
        Affiliate affiliate = new Affiliate(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");

        Mockito.when(affiliateRepositoryMock.save(any(Affiliate.class))).thenReturn(affiliate);
        Mockito.when(affiliateRepositoryMock.existsById(anyLong())).thenAnswer(i -> i.getArguments()[0] == id);

        Assertions.assertThrowsExactly(DataValidationException.class, () -> affiliateService.updateAffiliate(id, affiliateDTOInvalidId));

    }

    @Test
    void testUpdateAffiliateNotExists() {

        // Id not found
        Long id = Long.valueOf(1);
        AffiliateDTO affiliateDTOInvalidId = new AffiliateDTO(null, "Juan Perez", 20, "juanperez@mail.com");
        Affiliate affiliate = new Affiliate(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");

        Mockito.when(affiliateRepositoryMock.save(any(Affiliate.class))).thenReturn(affiliate);
        Mockito.when(affiliateRepositoryMock.existsById(anyLong())).thenReturn(false);

        Assertions.assertThrowsExactly(DataValidationException.class, () -> affiliateService.updateAffiliate(id, affiliateDTOInvalidId));
    }

    @Test
    void testUpdateAffiliateSuccess() {

        Long id = Long.valueOf(1);
        AffiliateDTO affiliateDTO = new AffiliateDTO(null, "Juan Perez", 20, "juanperez@mail.com");
        Affiliate affiliate = new Affiliate(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");

        Mockito.when(affiliateRepositoryMock.save(any(Affiliate.class))).thenReturn(affiliate);
        Mockito.when(affiliateRepositoryMock.findById(anyLong())).thenReturn(Optional.of(affiliate));
        Mockito.when(affiliateRepositoryMock.existsById(anyLong())).thenReturn(true);

        AffiliateDTO expected = new AffiliateDTO(Long.valueOf(1), "Juan Perez", 20, "juanperez@mail.com");

        Assertions.assertEquals(expected, affiliateService.updateAffiliate(id, affiliateDTO));

        // With null data
        AffiliateDTO affiliateDTONullData = new AffiliateDTO(id, null, null, null);

        Assertions.assertEquals(expected, affiliateService.updateAffiliate(id, affiliateDTONullData));
    }
}
