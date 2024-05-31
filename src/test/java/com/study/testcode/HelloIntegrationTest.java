package com.study.testcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.testcode.dto.RequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // 어플리케이션 전체가 로드 -> 모든 빈과 구성이 테스트에 포함
@AutoConfigureMockMvc   // MockMvc 객체를 의존 주입 받을 수 있게 해줌 -> 실제 주입은 @Autowired 로 수행
@Transactional  // 테스트가 끝나면 롤백
public class HelloIntegrationTest implements HelloTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HelloRepository helloRepository;

    private static final String BASE_URL = "/hello";

    @Test
    @DisplayName("Hello 생성 통합 테스트")
    @Transactional
    @DirtiesContext // 각각의 테스트를 독립적으로 수행
    void createHello() throws Exception{
        // given
        RequestDto requestDto = RequestDto.builder()
                .message(TEST_USER_MESSAGE)
                .build();

        String param = objectMapper.writeValueAsString(requestDto);

        // when
        ResultActions result = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(param))
                .andDo(print());

        // then
        result.andExpect(status().isCreated());

        assertThat(helloRepository.existsById(HELLO.getId())).isTrue();
    }

    @Nested
    @DisplayName("Hello 조회 통합 테스트")
    @Transactional
    @DirtiesContext // 각각의 테스트를 독립적으로 수행
    class getEntity{
        @BeforeEach
        void setup() {
            helloRepository.save(HELLO);
        }

        @Test
        @DisplayName("Hello 조회 통합 테스트")
        void getHello() throws Exception{
            // given
            Long id = HELLO.getId();

            // when
            ResultActions result = mockMvc.perform(get(BASE_URL + "/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            result.andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.message").value("Hello Test"));
        }
    }

    @Nested
    @DisplayName("Hello 수정 통합 테스트")
    @Transactional
    @DirtiesContext // 각각의 테스트를 독립적으로 수행
    class setEntity{
        Hello savedHello;

        @BeforeEach
        void setup() {
            savedHello = helloRepository.save(HELLO);
        }

        @Test
        @DisplayName("Hello 수정 통합 테스트")
        void setHello() throws Exception{
            // given
            Long id = HELLO.getId();

            RequestDto requestDto = RequestDto.builder()
                    .message(UPDATE_TEST_USER_MESSAGE)
                    .build();
            String param = objectMapper.writeValueAsString(requestDto);

            // when
            ResultActions result = mockMvc.perform(put(BASE_URL + "/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(param))
                    .andDo(print());

            // then
            result.andExpect(status().isOk());
            assertThat(savedHello.getMessage()).isEqualTo(UPDATE_TEST_USER_MESSAGE);
        }
    }
}
