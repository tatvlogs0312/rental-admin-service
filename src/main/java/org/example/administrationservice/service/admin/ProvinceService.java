package org.example.administrationservice.service.admin;

import org.example.administrationservice.models.provinces.DistrictResDTO;
import org.example.administrationservice.models.provinces.ProvinceResDTO;
import org.example.administrationservice.models.provinces.WardResDTO;

import java.util.List;

public interface ProvinceService {
    List<ProvinceResDTO> getProvince();
    List<DistrictResDTO> getDistrictsByProvinceId(String provinceId);
    List<WardResDTO> getWardByDistrictId(String districtId);
}
