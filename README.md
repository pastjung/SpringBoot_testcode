# 테스트 코드 작성법 학습 프로젝트
앞으로의 코드 테스트를 위한 테스트 코드 작성 방법 학습
[프로젝트 계획서](https://pastjung.notion.site/32906642450b4c2ca602db02d597b6c7?pvs=4)

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
- Spring Test
- Mockito
- JUnit 5
- Jackson
- AssertJ
### Service 테스트
- Mockito
- JUnit 5
- AssertJ
### Repository 테스트
- Spring Boot Test
- JUnit 5
- AssertJ

## 2. 통합 테스트
- Spring Boot Test
- Spring Test
- JUnit 5
- Jackson
- AssertJ

## 3. 예외 테스트
- GlobalException
- MockMvc
- ResultActions
