package org.example.administrationservice.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.administrationservice.models.UtilityDTO;
import org.example.administrationservice.models.request.UtilityReqDTO;
import org.example.administrationservice.service.admin.UtilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utility")
public class UtilityController {

    private final UtilityService utilityService;

//    @Secured
    @GetMapping("/find-all")
    public ResponseEntity<Object> findAll() {
        List<UtilityDTO> utilityDTOS = utilityService.findAll();
        return new ResponseEntity<>(utilityDTOS, HttpStatus.OK);
    }

    @Operation(summary = "Api thêm dịch vụ")
    @PostMapping(value = "add-utility", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUtility(@RequestBody UtilityReqDTO req) {
        utilityService.addUtility(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api sửa dịch vụ")
    @PutMapping(value = "update-utility", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUtility(@RequestBody UtilityReqDTO req) {
        utilityService.updateUtility(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api xóa dịch vụ")
    @DeleteMapping(value = "delete-utility", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUtility(@RequestBody UtilityReqDTO req) {
        utilityService.deleteUtility(req.getCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
