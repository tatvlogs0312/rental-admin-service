package org.example.administrationservice.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.models.provinces.BaseDistrictResDTO;
import org.example.administrationservice.models.provinces.BaseProvinceResDTO;
import org.example.administrationservice.models.provinces.BaseWardResDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProvinceProxy {

    private final BaseProxy baseProxy;

    @Value("${custom.properties.province-url}")
    private String PROVINCE_URL;

    public BaseProvinceResDTO getProvince() {
        try {
            String url = PROVINCE_URL + "/api/province/";
            return (BaseProvinceResDTO) baseProxy.get(url, BaseProvinceResDTO.class);
        } catch (Exception e) {
            log.info("ProvinceProxy getProvince - Exception: {}", e.getMessage());
        }
        return new BaseProvinceResDTO();
    }

    public BaseDistrictResDTO getDistrict(String provinceId) {
        try {
            String url = PROVINCE_URL + "/api/province/district/" + provinceId;
            return (BaseDistrictResDTO) baseProxy.get(url, BaseDistrictResDTO.class);
        } catch (Exception e) {
            log.info("ProvinceProxy getDistrict - Exception: {}", e.getMessage());
        }
        return new BaseDistrictResDTO();
    }

    public BaseWardResDTO getWard(String districtId) {
        try {
            String url = PROVINCE_URL + "/api/province/ward/" + districtId;
            return (BaseWardResDTO) baseProxy.get(url, BaseWardResDTO.class);
        } catch (Exception e) {
            log.info("ProvinceProxy getWard - Exception: {}", e.getMessage());
        }
        return new BaseWardResDTO();
    }
}
