package org.example.administrationservice.service.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.models.provinces.*;
import org.example.administrationservice.proxy.ProvinceProxy;
import org.example.administrationservice.service.admin.ProvinceService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceProxy provinceProxy;

    @Override
    public List<ProvinceResDTO> getProvince() {
        BaseProvinceResDTO provinceResDTO = provinceProxy.getProvince();
        if (!CollectionUtils.isEmpty(provinceResDTO.getResults())) {
            return provinceResDTO.getResults();
        }
        return new ArrayList<>();
    }

    @Override
    public List<DistrictResDTO> getDistrictsByProvinceId(String provinceId) {
        BaseDistrictResDTO baseDistrictResDTO = provinceProxy.getDistrict(provinceId);
        if (!CollectionUtils.isEmpty(baseDistrictResDTO.getResults())) {
            return baseDistrictResDTO.getResults();
        }
        return new ArrayList<>();
    }

    @Override
    public List<WardResDTO> getWardByDistrictId(String districtId) {
        BaseWardResDTO baseWardResDTO = provinceProxy.getWard(districtId);
        if (!CollectionUtils.isEmpty(baseWardResDTO.getResults())) {
            return baseWardResDTO.getResults();
        }
        return new ArrayList<>();
    }
}