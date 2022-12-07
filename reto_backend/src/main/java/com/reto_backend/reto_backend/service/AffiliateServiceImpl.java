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

import com.reto_backend.reto_backend.dto.AffiliateDTO;
import com.reto_backend.reto_backend.error.DataValidationException;
import com.reto_backend.reto_backend.model.Affiliate;
import com.reto_backend.reto_backend.repository.AffiliateRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class AffiliateServiceImpl implements AffiliateService{

    @Autowired
    private AffiliateRepository affiliateRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Validator validator;

    AffiliateServiceImpl(Validator validator){
        this.validator = validator;
    }

    // Create a new Affiliate
    @Override
    public AffiliateDTO createAffiliate(AffiliateDTO affiliateDTO) throws DataValidationException{

        // Validate data
        this.validateAffiliate(affiliateDTO);
        
        // Create affiliate entity and save it to DB
        Affiliate affiliate = convertDtoToEntity(affiliateDTO);
        affiliate.setId(null);
        AffiliateDTO response = convertEntityToDto(affiliateRepository.save(affiliate));

        return response;
    }

    // Get all Affiliates
    @Override
    public List<AffiliateDTO> getAffiliates() {

        return affiliateRepository.findAll()
                                    .stream()
                                    .map(this::convertEntityToDto)
                                    .collect(Collectors.toList());
                                    
    }

     // Get one Affiliate
    @Override
    public AffiliateDTO getAffiliateById(Long id) {

        // Get the affiliate with the input id
        Optional<Affiliate> affiliate = affiliateRepository.findById(id);

        // If there are not results, return null
        if(affiliate.isEmpty()){
            return null;
        }

        //if there are results, return the affiliate
        return convertEntityToDto(affiliate.get());

    }

    // Delete one Affiliate
    @Override
    public boolean deleteAffiliate(Long id) {

        // Check if the affiliate exists
        boolean exists = affiliateRepository.existsById(id);
        if(!exists){
            return false;
        }

        // If the affiliate exists, delete it
        affiliateRepository.deleteById(id);

        // Check if the affiliate still exists (if the deletion failed);
        exists = affiliateRepository.existsById(id);
        if(exists){
            return false;
        }
        return true;

    }

    // Update one Affiliate
    @Override
    public AffiliateDTO updateAffiliate(Long id, AffiliateDTO affiliateDTO) throws DataValidationException{

        boolean exists = affiliateRepository.existsById(id);

        // If the id in the request body is different to URL parameter, and if the affiliate exists
        if(affiliateDTO.getId() != null && id != affiliateDTO.getId()){
            String errorMessage = "Affiliate id cannot be changed";
            throw new DataValidationException(errorMessage);
        }else if(!exists){
            String errorMessage = "Affiliate doesn't exist";
            throw new DataValidationException(errorMessage);
        }

        // Create the new object and update the data
        AffiliateDTO updatedAffiliateDTO = convertEntityToDto(affiliateRepository.findById(id).get());
        updatedAffiliateDTO.setName(Objects.nonNull(affiliateDTO.getName()) ? affiliateDTO.getName() : updatedAffiliateDTO.getName());
        updatedAffiliateDTO.setAge(Objects.nonNull(affiliateDTO.getAge()) ? affiliateDTO.getAge() : updatedAffiliateDTO.getAge());
        updatedAffiliateDTO.setMail(Objects.nonNull(affiliateDTO.getMail()) ? affiliateDTO.getMail() : updatedAffiliateDTO.getMail());

        // Check if the new data is valid
        this.validateAffiliate(updatedAffiliateDTO);

        // Create affiliate entity and save it to DB
        Affiliate affiliate = convertDtoToEntity(updatedAffiliateDTO);
        AffiliateDTO response = convertEntityToDto(affiliateRepository.save(affiliate));

        return response;
    }

    public AffiliateDTO convertEntityToDto(Affiliate affiliate){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        AffiliateDTO affiliateDTO = new AffiliateDTO();
        affiliateDTO = modelMapper.map(affiliate, AffiliateDTO.class);
        return affiliateDTO;
    }

    public Affiliate convertDtoToEntity(AffiliateDTO affiliateDTO){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Affiliate affiliate = new Affiliate();
        affiliate = modelMapper.map(affiliateDTO, Affiliate.class);
        return affiliate;
    }

    public void validateAffiliate(AffiliateDTO affiliateDTO) throws DataValidationException{

        // Validate Affiliate fields
        Set<ConstraintViolation<AffiliateDTO>> violations = validator.validate(affiliateDTO);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                                            .map(e -> e.getMessageTemplate())
                                            .collect(Collectors.joining(", "));
            throw new DataValidationException(errorMessage);
        }

    }

    public void setMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    
}
