# Test Code Convention

## 테스트 분류 및 네이밍

| 유형 | 파일명 패턴 | 예시 |
|---|---|---|
| 단위 테스트 | `{Class}UnitTest.java` 또는 `{Class}Test.java` | `BookmarkServiceUnitTest.java` |
| 통합 테스트 | `{Class}IntegrationTest.java` | `BookmarkServiceIntegrationTest.java` |

- 패키지 구조는 `src/main/java`의 도메인 구조를 미러링하라

## 단위 테스트 설정

| 항목 | 규칙 |
|---|---|
| 클래스 어노테이션 | `@ExtendWith(MockitoExtension.class)` |
| 테스트 대상 | `@InjectMocks` |
| 의존성 | `@Mock` |
| 검증 라이브러리 | AssertJ (`assertThat`, `assertSoftly`, `assertThatThrownBy`) |

## 통합 테스트 설정

| 항목 | 규칙 |
|---|---|
| 클래스 어노테이션 | `@TCSpringBootTest` (Testcontainers + PostgreSQL) |
| 의존성 주입 | `@Autowired` |
| DB 초기화 | `@Sql(scripts = "classpath:sql/truncate_all.sql", executionPhase = BEFORE_TEST_METHOD)` |
| Controller 테스트 | `@AutoConfigureMockMvc` + `@WithMockLoginUser` 추가 |

## 테스트 메서드 작성

| 항목 | 규칙 | 예시 |
|---|---|---|
| 메서드명 | 한글 서술형 | `연속학습일수_업데이트에_성공한다()` |
| 그룹화 | `@Nested` + `@DisplayName` | `@DisplayName("북마크를 추가할 때")` |
| 구간 구분 | `// given` / `// when` / `// then` 주석 필수 | — |

## Fixture

| 항목 | 규칙 |
|---|---|
| 위치 | `src/test/java/gravit/code/{domain}/fixture/` |
| 메서드명 | 한글로 시나리오 표현 (e.g., `LearningFixture.당일_학습_완료(userId)`) |
| 복잡한 Entity | `{Domain}FixtureBuilder` 클래스 사용 (기본값 제공 + 필드 오버라이드) |
| Entity id 설정 | `ReflectionTestUtils.setField(entity, "id", 1L)` |
| VO 타입 명시 | `setField(user, "level", level, UserLevel.class)` |