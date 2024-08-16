package org.example.administrationservice.controller.sms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.models.sms.SmsResDTO;
import org.example.administrationservice.service.sms.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
@Slf4j
public class SmsController {

    private final SmsService service;

    @PostMapping("/send")
    public ResponseEntity<Object> sendSms(@RequestBody SmsResDTO req) {
        service.sendSms(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
