# 테스트 유형 분류 기준

## 단위 테스트 (UnitTest)

대상: Service, Facade의 비즈니스 로직
조건: 외부 의존성(DB, Redis 등) 없이 검증 가능한 경우

| 항목 | 값 |
|---|---|
| 어노테이션 | `@ExtendWith(MockitoExtension.class)` |
| 대상 클래스 | `@InjectMocks` |
| 의존성 | `@Mock` |
| 파일명 | `{Class}UnitTest.java` 또는 `{Class}Test.java` |

## 통합 테스트 (IntegrationTest)

대상: DB 연동, 여러 레이어 통합 검증, Repository 쿼리 검증
조건: 실제 DB나 외부 시스템 연동이 필요한 경우

| 항목 | 값 |
|---|---|
| 어노테이션 | `@TCSpringBootTest` (Testcontainers + PostgreSQL) |
| 의존성 주입 | `@Autowired` |
| DB 초기화 | `@Sql(scripts = "classpath:sql/truncate_all.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)` |
| Controller 테스트 | `@AutoConfigureMockMvc` + `@WithMockLoginUser` 추가 |
| 파일명 | `{Class}IntegrationTest.java` |

## 판단 플로우

1. 대상 클래스가 Repository를 직접 호출하고, 쿼리 결과가 핵심인가? → 통합 테스트
2. 대상 클래스가 여러 Service를 조합하며 트랜잭션 경계가 중요한가? → 통합 테스트
3. Mock으로 의존성을 대체해도 로직 검증이 충분한가? → 단위 테스트
4. 확신이 없으면 단위 테스트를 우선 작성하라