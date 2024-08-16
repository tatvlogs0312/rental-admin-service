package org.example.administrationservice.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.administrationservice.models.request.BillCreateReqDTO;
import org.example.administrationservice.models.request.BillSearchReqDTO;
import org.example.administrationservice.service.admin.BillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @Operation(summary = "API tạo hóa đơn")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createBill(@RequestBody BillCreateReqDTO req) {
        billService.createBill(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "API thanh toán hóa đơn")
    @PostMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> paymentBill(@RequestBody BillCreateReqDTO req) {
        billService.paymentBill(req.getBillId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "API tìm kiếm hóa đơn")
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> search(@RequestBody BillSearchReqDTO req) {
        return new ResponseEntity<>(billService.searchBill(req), HttpStatus.OK);
    }

}
