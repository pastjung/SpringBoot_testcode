package com.study.testcode.dto.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ExceptionResponse {
    String errorMessage;

    public static ResponseEntity<ExceptionResponse> ofData(String message, Integer httpStatus) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .errorMessage(message)
                .build();
        return ResponseEntity.status(httpStatus).body(exceptionResponse);
    }
}
