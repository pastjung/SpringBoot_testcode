# 테스트 코드 작성법 학습 프로젝트
앞으로의 코드 테스트를 위한 테스트 코드 작성 방법 학습

## 개발 환경
- Java 17
- Gradle 8.7
- Spring Boot : 3.2.5
- Database : H2-database
- Tools : IntelliJ IDEA Community

## 기능
- `Hello` 생성, 조회, 수정

## 1. 단위 테스트
### Controller 테스트
- **Spring Boot Test**
  - @WebMvcTest : Spring MVC 구성 요소에 대한 단위 테스트를 수행할 수 있게 해줌
- **Spring Test**
  - MockMvc : 스프링 MVC의 모킹된 버전으로, 웹 애플리케이션을 실제 서버에 배포하지 않고도 HTTP 요청 및 응답을 테스트를 할 수 있게 해줌
  - WebApplicationContext : 스프링의 웹 애플리케이션 컨텍스트를 제공하며, MockMvc를 설정하는 데 사용
  - CharacterEncodingFilter : 요청과 응답의 문자 인코딩을 설정하는 필터
- **Mockito**
  - @MockBean : 스프링 애플리케이션 컨텍스트 내에서 Mock 객체를 생성하고 주입
- **Jackson**
  - ObjectMapper : JSON 데이터를 객체로 변환하거나 객체를 JSON 데이터로 변환
- **JUnit 5**
  - @Test, @BeforeEach, @DisplayName, @Nested
- **AssertJ**
  - assertThat
### Service 테스트
- **Mockito**
  - @ExtendWith(MockitoExtension.class) : Mockito와 JUnit 5를 통합하여 사용하는 애노테이션
  - @InjectMocks : Mockito를 사용하여 객체를 생성
  - @Mock : 의존성을 모킹하여 실제 구현체 대신 테스트 시 시용할 모의 객체 생성
  - Mockito.verify(), Mockito.given(), Mockito.times() : 메서드의 동작을 모킹하고 호출 여부 및 횟수를 검증
- **JUnit 5**
  - @Nested : 중첩된 테스트 클래스를 정의하여 관련된 테스트를 그룹화
  - @BeforeEach, @Test, @DisplayName
- **AssertJ**
  - assertThat
### Repository 테스트
- **Spring Boot Test**
  - @DataJpaTest : JPA 관련 컴포넌트(ex. 리포지토리)를 테스트하기 위해 사용, 이때 내장된 데이터베이스를 사용
  - @AutoConfigureTestDatabase : 테스트에 사용할 데이터베이스를 구성. 이때 임베디드 데이터베이스를 사용하지 않도록 설정
  - @Transactional : 각 테스트 메서드가 완료된 후 데이터베이스의 상태를 롤백하여 테스트 간의 데이터 격리를 보장
  - @TestMethodOrder : 테스트 메서드의 실행 순서를 지정. 이때 @Order 를 사용해 순서를 정할 수 있음
  - @BeforeEach : 각 테스트 메서드가 실행되기 전에 공통 설정 작업을 수행
  - @Autowired : 의존성 주입을 사용하여 테스트 대상 객체를 주입
- **JUnit 5**
  - @Test, @DisplayName, @Order, @BeforeEach
- **AssertJ**
  - assertThat

## 2. 통합 테스트
- **Spring Boot Test**
  - @SpringBootTest : Spring Boot 애플리케이션 컨텍스트를 로드하고 통합 테스트를 수행하게 해줌
  - @Transactional : 테스트가 끝난 후 롤백을 수행하여 테스트 간의 데이터를 격리할 수 있게 해줌
  - @DirtiesContext : 각 테스트 메서드 후에 애플리케이션 컨텍스트를 다시 로드하여 테스트가 독립적으로 수행될 수 있게 함
- **Spring Test**
  - @AutoConfigureMockMvc : MockMvc 객체를 자동으로 구성해 실제 서버에 배포하지 않고도 HTTP 요청 및 응답을 테스트할 수 있게 해줌
  - @Autowired : MockMvc, ObjectMapper, HelloRepository 등의 빈을 주입 받음
- **Jackson**
  - @ObjectMapper : JSON 데이터를 객체로 변환하거나 객체를 JSON 데이터로 변환
- **JUnit 5**
  - @Test, @BeforeEach, @DisplayName, @Nested, @TestMethodOrder, @order, @MethodOrder.OrderAnnotation
- **AssertJ**
  - assertThat

## 3. 예외 테스트
- mockMvc
