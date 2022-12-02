package com.reto_backend.reto_backend.service;

import java.util.List;

import com.reto_backend.reto_backend.dto.AffiliateDTO;

public interface AffiliateService {
    List<AffiliateDTO> getAffiliates();
    AffiliateDTO getAffiliateById();
    AffiliateDTO createAffiliate(AffiliateDTO affiliateDTO);
}
