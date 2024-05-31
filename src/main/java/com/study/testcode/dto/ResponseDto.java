package com.study.testcode.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto {
    String message;

    public ResponseDto(String message) {
        this.message = message;
    }
}
