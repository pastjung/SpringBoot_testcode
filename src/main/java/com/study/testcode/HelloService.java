package com.study.testcode;

import com.study.testcode.dto.RequestDto;
import com.study.testcode.dto.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelloService {
    private final HelloRepository helloRepository;

    @Transactional
    public void createHello(RequestDto requestDto) {
        Hello hello = new Hello(requestDto.getMessage());

        helloRepository.save(hello);
    }

    public ResponseDto getHello(Long id) {
        Hello savedHello = helloRepository.findById(id).orElse(null);

        if(savedHello == null) {
            throw new IllegalArgumentException("Hello is empty.");
        }

        return new ResponseDto(savedHello.getMessage());
    }

    @Transactional
    public void updateHello(Long id, RequestDto requestDto) {
        Hello savedHello = helloRepository.findById(id).orElse(null);

        if(savedHello == null) {
            throw new IllegalArgumentException("Hello is empty.");
        }

        savedHello.update(requestDto.getMessage());
    }
}
