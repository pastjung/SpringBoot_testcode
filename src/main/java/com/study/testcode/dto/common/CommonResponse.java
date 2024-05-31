package com.study.testcode.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor (access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)  // Object 를 응답할 때 Null 인 필드가 있다면 JSON 으로 파싱할 때 넣지 않는다.
public class CommonResponse<T> {
    private Integer statusCode;
    private T data;

    public static <T> CommonResponse<T> ofData(T data) {
        return CommonResponse.<T>builder()
                .data(data)
                .build();
    }

    public static <T> ResponseEntity<CommonResponse<T>> ofDataWithHttpStatus(HttpStatus httpStatus, T data) {
        CommonResponse<T> commonResponse = CommonResponse.ofData(data);
        return ResponseEntity.status(httpStatus).body(commonResponse);
    }
}