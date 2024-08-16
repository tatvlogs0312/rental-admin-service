package org.example.administrationservice.service.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.entity.Utility;
import org.example.administrationservice.exception.ApplicationException;
import org.example.administrationservice.exception.ExceptionEnums;
import org.example.administrationservice.models.UtilityDTO;
import org.example.administrationservice.models.request.UtilityReqDTO;
import org.example.administrationservice.repository.UtilityRepository;
import org.example.administrationservice.service.admin.UtilityService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilityServiceImpl implements UtilityService {

    private final UtilityRepository utilityRepository;

    @Override
    public List<UtilityDTO> findAll() {
        List<Utility> utilities = utilityRepository.findAll();
        if (!CollectionUtils.isEmpty(utilities)) {
            return utilities.stream()
                    .map(x -> UtilityDTO
                            .builder()
                            .id(x.getId())
                            .code(x.getCode())
                            .name(x.getName())
                            .price(x.getPrice().toString() + "/" + x.getUnit())
                            .build())
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public void addUtility(UtilityReqDTO req) {
        Optional<Utility> utilityOptional = utilityRepository.findFirstByCode(req.getCode());
        if (utilityOptional.isPresent()) {
            throw new ApplicationException(ExceptionEnums.EX_UTILITY_CODE_EXIST);
        }

        Utility utility = new Utility();
        utility.setId(UUID.randomUUID().toString());
        utility.setCode(req.getCode());
        utility.setName(req.getName());
        utility.setPrice(req.getPrice());
        utility.setUnit(req.getUnit());

        utilityRepository.save(utility);
    }

    @Override
    public void updateUtility(UtilityReqDTO req) {
        Optional<Utility> utilityOptional = utilityRepository.findFirstByCode(req.getCode());
        if (utilityOptional.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_UTILITY_CODE_NOT_EXIST);
        }

        Utility utility = utilityOptional.get();
        utility.setName(req.getName());
        utility.setPrice(req.getPrice());
        utility.setUnit(req.getUnit());

        utilityRepository.save(utility);
    }

    @Override
    public void deleteUtility(String utilityCode) {
        Optional<Utility> utilityOptional = utilityRepository.findFirstByCode(utilityCode);
        if (utilityOptional.isEmpty()) {
            throw new ApplicationException(ExceptionEnums.EX_UTILITY_CODE_NOT_EXIST.getMessage());
        }

        utilityRepository.deleteAllByCode(utilityCode);
    }
}
