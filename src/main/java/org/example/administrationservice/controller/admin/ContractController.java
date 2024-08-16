package org.example.administrationservice.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.models.request.ContractReqDTO;
import org.example.administrationservice.models.request.ContractSearchReqDTO;
import org.example.administrationservice.models.request.ContractUploadReqDTO;
import org.example.administrationservice.service.admin.ContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
@Slf4j
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "Api tạo hợp đồng")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createContract(@RequestBody ContractReqDTO req) {
        contractService.createContract(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api chấm dứt hợp đồng")
    @PutMapping(value = "/termination", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> terminationContract(@RequestBody ContractReqDTO req) {
        contractService.terminationContract(req.getContractCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api upload cccd/cmnt")
    @PostMapping(value = "/upload-identity", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadIdentityCard(ContractUploadReqDTO req) {
        contractService.uploadIdentityCard(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api download cccd/cmnd")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Object> downloadIdentity(@PathVariable String id) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(contractService.getIdentityById(id));
    }

    @Operation(summary = "Api active hợp đồng")
    @PostMapping(value = "/effective", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateEffective(@RequestBody ContractUploadReqDTO req) {
        contractService.updateStatusToEffective(req.getContractCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Api tìm kiếm hợp đồng")
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchContract(@RequestBody ContractSearchReqDTO req) {
        return new ResponseEntity<>(contractService.searchContract(req), HttpStatus.OK);
    }

    @Operation(summary = "Api xem chi tiết hợp đồng")
    @PostMapping(value = "/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getContractDetail(@RequestBody ContractSearchReqDTO req) {
        return new ResponseEntity<>(contractService.getContractDetail(req.getContractCode()),
                HttpStatus.OK);
    }
}
