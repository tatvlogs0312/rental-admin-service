package org.example.administrationservice.controller.authenticator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.administrationservice.models.auth.login.LoginReqDTO;
import org.example.administrationservice.models.auth.register.NewUserReqDTO;
import org.example.administrationservice.service.authentication.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReqDTO req) {
        return new ResponseEntity<>(service.login(req), HttpStatus.OK);
    }

    @PostMapping("/create-account")
    public ResponseEntity<Object> createAccount(@RequestBody NewUserReqDTO req) {
        service.createNewUser(req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
