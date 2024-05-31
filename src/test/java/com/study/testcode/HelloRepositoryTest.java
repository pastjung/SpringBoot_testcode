package com.study.testcode;

import com.study.testcode.dto.RequestDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest    // Repository 객체를 의존주입받을 수 있게 함 -> @Autowired 사용 필요
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // @Order(n) 를 사용해서 테스트 순서 정하기 가능
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    //  임베디드 데이터 베이스를 사용 안한다는 선언
class HelloRepositoryTest implements HelloTest{
    @Autowired
    private HelloRepository helloRepository;

    @BeforeEach // 테스트 실행 전 실행
    void setup(){
        System.out.println("=============================== setup ==============================");
    }

    @Test
    @Order(1)
    @DisplayName("Hello 생성")
    void createHello(){

        // given
        // HelloTest 의 hello 객체 사용

        // when
        Hello savedHello = helloRepository.save(HELLO);

        // then
        assertThat(savedHello).isNotNull();
        assertThat(savedHello.getMessage()).isEqualTo(HELLO.getMessage());
    }

    @Test
    @Order(2)
    @DisplayName("Hello 목록 조회")
    void getHello(){

        // given
        helloRepository.save(HELLO);

        // when
        List<Hello> findList = helloRepository.findAll();

        // then
        assertThat(findList).isNotEmpty();
        assertThat(findList.size()).isEqualTo(1);
    }

    @Test
    @Order(3)
    @DisplayName("Hello 메시지 수정")
    void updateHello(){

        // given
        Hello savedHello = helloRepository.save(HELLO);

        // when
        savedHello.update("Hi");

        // then
        Hello findEntity = helloRepository.findById(savedHello.getId()).orElse(null);
        assertThat(findEntity).isNotNull();
        assertThat(findEntity.getMessage()).isEqualTo("Hi");
    }
}