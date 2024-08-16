package org.example.administrationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.service.admin.ProvinceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/province")
@RequiredArgsConstructor
@Slf4j
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping()
    @Operation(summary = "API danh sách tỉnh thành")
    public ResponseEntity<Object> getProvinces() {
        return new ResponseEntity<>(provinceService.getProvince(), HttpStatus.OK);
    }

    @GetMapping("/district")
    @Operation(summary = "API danh sách quận huyện")
    public ResponseEntity<Object> getDistrict(@RequestParam String provinceId) {
        return new ResponseEntity<>(provinceService.getDistrictsByProvinceId(provinceId), HttpStatus.OK);
    }

    @GetMapping("/ward")
    @Operation(summary = "API danh sách phường xã")
    public ResponseEntity<Object> getWard(@RequestParam String districtId) {
        return new ResponseEntity<>(provinceService.getWardByDistrictId(districtId), HttpStatus.OK);
    }
}
