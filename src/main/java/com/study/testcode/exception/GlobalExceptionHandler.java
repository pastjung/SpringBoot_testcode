package com.study.testcode.exception;

import com.study.testcode.dto.common.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(Exception ex) {
        return ExceptionResponse.ofData(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
}
