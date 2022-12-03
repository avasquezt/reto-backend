package com.reto_backend.reto_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reto_backend.reto_backend.dto.AffiliateDTO;
import com.reto_backend.reto_backend.model.Affiliate;
import com.reto_backend.reto_backend.repository.AffiliateRepository;

@Service
public class AffiliateServiceImpl implements AffiliateService{

 
    @Autowired
    private AffiliateRepository affiliateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AffiliateDTO> getAffiliates() {
        return affiliateRepository.findAll()
            .stream()
            .map(this::convertEntityToDto)
            .collect(Collectors.toList());
    }

    @Override
    public AffiliateDTO getAffiliateById(Long id) {
        return convertEntityToDto(affiliateRepository.findById(id).get());
    }

    @Override
    public AffiliateDTO createAffiliate(AffiliateDTO affiliateDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    private AffiliateDTO convertEntityToDto(Affiliate affiliate){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        AffiliateDTO affiliateDTO = new AffiliateDTO();
        affiliateDTO = modelMapper.map(affiliate, AffiliateDTO.class);
        return affiliateDTO;
    }

    private Affiliate convertDtoToEntity(AffiliateDTO affiliateDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Affiliate affiliate = new Affiliate();
        affiliate = modelMapper.map(affiliateDTO, Affiliate.class);
        return affiliate;
    }
    
}
