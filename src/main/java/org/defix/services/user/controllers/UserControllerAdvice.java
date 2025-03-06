package org.defix.services.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.defix.services.ControllerAdviceUtils;
import org.defix.services.user.exceptions.UserAlreadyExistException;
import org.defix.services.user.exceptions.UserNotFoundException;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ControllerAdviceUtils.prepareResponseData(
                        e, HttpStatus.CONTINUE.value()
                )
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ControllerAdviceUtils.prepareResponseData(
                        e, HttpStatus.NOT_FOUND.value()
                )
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ControllerAdviceUtils.prepareResponseData(
                        e, HttpStatus.FORBIDDEN.value()
                )
        );
    }
}
