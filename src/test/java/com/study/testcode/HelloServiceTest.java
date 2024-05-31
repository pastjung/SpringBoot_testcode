package com.study.testcode;

import com.study.testcode.dto.RequestDto;
import com.study.testcode.dto.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HelloServiceTest implements HelloTest{
    @InjectMocks
    HelloService helloService;

    @Mock
    HelloRepository helloRepository;

    RequestDto requestDto;
    ResponseDto responseDto;

    @Nested
    @DisplayName("Hello 생성 테스트")
    class createHello {

        @BeforeEach
        void setup() {
            requestDto = RequestDto.builder()
                    .message(HELLO.getMessage())
                    .build();
        }

        @Test
        @DisplayName("Hello 생성 성공")
        void create() {
            //given
            given(helloRepository.save(any(Hello.class))).willReturn(HELLO);

            //when
            helloService.createHello(requestDto);

            //then
            verify(helloRepository).save(any(Hello.class));
        }
    }

    @Nested
    @DisplayName("Hello 조회 테스트")
    class getHello {
        Long id;

        @BeforeEach
        void setup() {
            id = 1L;
            requestDto = RequestDto.builder()
                    .message(HELLO.getMessage())
                    .build();
        }

        @Test
        @DisplayName("Hello 조회 성공")
        void getEntity() {
            //given
            given(helloRepository.findById(eq(id))).willReturn(Optional.ofNullable(HELLO));

            //when
            ResponseDto responseDto = helloService.getHello(id);

            //then
            verify(helloRepository).findById(eq(id)); // findById 호출 확인
            assertThat(responseDto).isNotNull();
            assertThat(responseDto.getMessage()).isEqualTo(TEST_USER_MESSAGE);
        }
    }

    @Nested
    @DisplayName("Hello 수정 테스트")
    class updateHello {
        Long id;

        @BeforeEach
        void setup() {
            id = 1L;
            requestDto = RequestDto.builder()
                    .message(UPDATE_HELLO.getMessage())
                    .build();
        }

        @Test
        @DisplayName("Hello 수정 성공")
        void put() {
            //given
            given(helloRepository.findById(eq(id))).willReturn(Optional.of(HELLO));

            //when
            helloService.updateHello(id, requestDto);

            //then
            verify(helloRepository).findById(eq(id));                           // findById 호출 확인
            assertThat(HELLO.getMessage()).isEqualTo(UPDATE_TEST_USER_MESSAGE);
            // HELLO 의 Message 가 변경되었는지 확인
        }
    }
}