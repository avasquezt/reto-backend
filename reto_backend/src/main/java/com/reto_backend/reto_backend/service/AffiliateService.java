package com.reto_backend.reto_backend.service;

import java.util.List;

import com.reto_backend.reto_backend.dto.AffiliateDTO;

public interface AffiliateService {
    
    AffiliateDTO createAffiliate(AffiliateDTO affiliateDTO);
    boolean deleteAffiliate(Long id);
    List<AffiliateDTO> getAffiliates();
    AffiliateDTO getAffiliateById(Long id);
    AffiliateDTO updateAffiliate(Long id, AffiliateDTO affiliateDTO);

}
