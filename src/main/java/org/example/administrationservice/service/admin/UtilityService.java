package org.example.administrationservice.service.admin;

import org.example.administrationservice.models.UtilityDTO;
import org.example.administrationservice.models.request.UtilityReqDTO;

import java.util.List;

public interface UtilityService {
    List<UtilityDTO> findAll();
    void addUtility(UtilityReqDTO req);
    void updateUtility(UtilityReqDTO req);
    void deleteUtility(String utilityCode);
}
