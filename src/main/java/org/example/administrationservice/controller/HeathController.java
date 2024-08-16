package org.example.administrationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.administrationservice.aop.Secured;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class HeathController {

    @Secured
    @GetMapping
    @Operation(summary = "ping")
    public ResponseEntity<Object> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
}
