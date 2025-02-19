package org.ziptegrity.auth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.ziptegrity.services.ControllerAdviceUtils;
import org.ziptegrity.services.user.exceptions.UserAlreadyExistException;
import org.ziptegrity.auth.services.exceptions.UsernameOrPasswordInvalidException;

import java.util.Arrays;
import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistException(UserAlreadyExistException e) {
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status).body(ControllerAdviceUtils.prepareResponseData(
                e, status
        ));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(ControllerAdviceUtils.prepareResponseData(
                e, status
        ));
    }

    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameOrPasswordInvalidException(UsernameOrPasswordInvalidException e) {
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status).body(ControllerAdviceUtils.prepareResponseData(
                e, status
        ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(AuthenticationException e) {
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status).body(ControllerAdviceUtils.prepareResponseData(
                e, status
        ));
    }
}
