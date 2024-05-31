package com.study.testcode;

import com.study.testcode.dto.RequestDto;
import com.study.testcode.dto.ResponseDto;
import com.study.testcode.dto.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hello")
public class HelloController {
    private final HelloService helloService;

    @PostMapping()
    public ResponseEntity<CommonResponse<Void>> createHello(@RequestBody RequestDto requestDto) {
        helloService.createHello(requestDto);
        return CommonResponse.ofDataWithHttpStatus(HttpStatus.CREATED, null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ResponseDto>> getHello(@PathVariable Long id) {
        ResponseDto responseDto = helloService.getHello(id);
        return CommonResponse.ofDataWithHttpStatus(HttpStatus.OK, responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> updateHello(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        helloService.updateHello(id, requestDto);
        return CommonResponse.ofDataWithHttpStatus(HttpStatus.OK, null);
    }
}
