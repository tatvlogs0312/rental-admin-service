package org.example.administrationservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ExceptionHandling {

    @ExceptionHandler(ApplicationException.class)
    ResponseEntity<Object> handlerApplicationException(ApplicationException e) {
        log.error("ExceptionHandling ApplicationException - Message: {}", e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now().toString(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationException.class)
    ResponseEntity<Object> handlerAuthorizationException(AuthorizationException e) {
        log.error("ExceptionHandling AuthorizationException - Message: {}", e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now().toString(), e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    ResponseEntity<Object> handlerForbiddenException(ForbiddenException e) {
        log.error("ExceptionHandling ForbiddenException - Message: {}", e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now().toString(), e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handlerRuntimeException(Exception e) {
        log.error("ExceptionHandling RuntimeException - Message: {}", ExceptionEnums.EX_INTERNAL_SERVER);
        return new ResponseEntity<>(new ErrorResponse(LocalDateTime.now().toString(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

