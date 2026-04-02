---
description: 테스트 코드를 작성하거나 수정할 때 적용되는 테스트 컨벤션
paths:
  - "src/test/java/**/*.java"
---

# Test code Convention

## 테스트 분류

- 단위 테스트는 `{Class}UnitTest.java` 또는 `{Class}Test.java`로 네이밍하라
- 통합 테스트는 `{Class}IntegrationTest.java`로 네이밍하라
- 패키지 구조는 `src/main/java`의 도메인 구조를 미러링하라

## 단위 테스트

- `@ExtendWith(MockitoExtension.class)`를 사용하라
- 테스트 대상은 `@InjectMocks`, 의존성은 `@Mock`으로 선언하라
- 검증에는 AssertJ를 사용하라 (`assertThat`, `assertSoftly`, `assertThatThrownBy`)

## 통합 테스트

- `@TCSpringBootTest` 커스텀 어노테이션을 사용하라 (Testcontainers + PostgreSQL)
- 의존성은 `@Autowired`로 주입하라
- DB 초기화가 필요하면 `@Sql(scripts = "classpath:sql/truncate_all.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)`를 붙여라
- Controller 통합 테스트에는 `@AutoConfigureMockMvc` + `@WithMockLoginUser`를 추가하라

## 테스트 메서드 작성

- 메서드명은 한글 서술형으로 작성하라 (e.g., `연속학습일수_업데이트에_성공한다()`)
- 관련 테스트는 `@Nested` + `@DisplayName`으로 그룹화하라
- `// given` / `// when` / `// then` 주석으로 구간을 구분하라

## Fixture

- Fixture는 `src/test/java/gravit/code/{domain}/fixture/` 에 위치시켜라
- 정적 팩토리 Fixture: 한글 메서드명으로 시나리오를 표현하라 (e.g., `LearningFixture.당일_학습_완료(userId)`)
- 복잡한 Entity는 `{Domain}FixtureBuilder` 클래스를 사용하라. 기본값을 제공하고 필요한 필드만 오버라이드하는 구조다
- Entity의 `id`는 `@GeneratedValue`이므로 테스트에서 `ReflectionTestUtils.setField()`로 설정하라
- VO 클래스를 `ReflectionTestUtils`로 설정할 때는 타입을 명시하라: `setField(user, "level", level, UserLevel.class)`
