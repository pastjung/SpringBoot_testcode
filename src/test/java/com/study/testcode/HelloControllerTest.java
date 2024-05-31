package com.study.testcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.testcode.dto.RequestDto;
import com.study.testcode.dto.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HelloController.class)    // Controller Layer 만 집중해서 테스트, @SpringBootTest 는 모든 Bean 을 Load
class HelloControllerTest implements HelloTest{
    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired  // webAppContextSetup 사용하기 위해 필요
    WebApplicationContext context;

    @MockBean // 단위테스트에서 사용, @Mock : 통합테스트에서 사용
    private HelloService helloService;

    private static final String BASE_URL = "/hello";
    RequestDto requestDto;
    ResponseDto responseDto;

    @BeforeEach
    void setup() {
        // Character SET 에 대한 깨짐 방지
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

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
        // @WithMockUser(roles = "USER") // Spring Security 을 사용하는 테스트에서 사용 : @AuthenticationPrincipal 의 인증을 대체해줌
        // 즉, @WithMockUser -> 가짜 사용자 생성
        void create() throws Exception {
            // given
            String body = objectMapper.writeValueAsString(requestDto);                  // JSON 형식으로 변환
            Mockito.doNothing().when(helloService).createHello(any(RequestDto.class));  // helloService.createHello() 메서드 호출시 아무 동작 X

            // when
            ResultActions result = mvc.perform(post(BASE_URL)
                    //.header(JwtUtil.AUTHORIZATION_HEADER, jwtToken)   // 로그인 등 헤더에 JWT 토큰 포함한 경우 사용 가능
                    .contentType(MediaType.APPLICATION_JSON)            // 요청의 Content-Type 명시
                    .content(body));                                    // 요청 바디에 JSON 데이터 삽입

            // 눈으로 결과 확인
            String content = result.andReturn().getResponse().getContentAsString();
            System.out.println("Response content: " + content + " :end");

            // then
            result.andExpect(status().isCreated());                       // 응답 상태 코드가 201 Created 인지 검증
                    //.andExpect(jsonPath("$").doesNotExist());   // 응답 본문이 비어 있는지 검증
        }
    }

    @Nested
    @DisplayName("Hello 조회 테스트")
    class getHello {
        Long id;
        @BeforeEach
        void setup() {
            id = 1L;
            responseDto = new ResponseDto(HELLO.getMessage());
        }

        @Test
        @DisplayName("Hello 조회 성공")
        void getEntity() throws Exception {
            // given
            given(helloService.getHello(eq(id))).willReturn(responseDto);    // 타입 비교 = any, 값 비교 = eq

            // when
            ResultActions result = mvc.perform(get(BASE_URL + "/{id}", id) // id 값을 사용하여 요청
                    .contentType(MediaType.APPLICATION_JSON));

            // 눈으로 결과 확인
            String content = result.andReturn().getResponse().getContentAsString();
            System.out.println("Response content: " + content + " :end");

            // then
            result.andExpect(status().isOk())                                                            // 응답 상태 코드가 200 Ok 인지 검증
                    .andExpect(jsonPath("$.data.message").value("Hello Test"));     // data.message 가 hello 의 message 인지 확인합니다.
        }
    }

    @Nested
    @DisplayName("Hello 수정 테스트")
    class setHello {
        Long id;
        @BeforeEach
        void setup() {
            id = 1L;
            requestDto = RequestDto.builder()
                    .message(HELLO.getMessage())
                    .build();
        }

        @Test
        @DisplayName("Hello 수정 성공")
        void setEntity() throws Exception {
            // given
            String body = objectMapper.writeValueAsString(requestDto);                  // JSON 형식으로 변환
            Mockito.doNothing().when(helloService).updateHello(eq(id), any(RequestDto.class));

            // when
            ResultActions result = mvc.perform(put(BASE_URL + "/{id}", id) // id 값을 사용하여 요청
                    .contentType(MediaType.APPLICATION_JSON)        // 요청의 Content-Type 명시
                    .content(body));                                // 요청 바디에 JSON 데이터 삽입

            // 눈으로 결과 확인
            String content = result.andReturn().getResponse().getContentAsString();
            System.out.println("Response content: " + content + " :end");

            // then
            result.andExpect(status().isOk());                           // 응답 상태 코드가 200 Ok 인지 검증
                    //.andExpect(jsonPath("$").doesNotExist());  // 응답 본문이 비어 있는지 검증
            verify(helloService, times(1)).updateHello(eq(id), any(RequestDto.class)); // 메서드 실행 횟수 검증
        }
    }
}