# Gravit Server

CS(Computer Science) 학습 보조 서비스 백엔드. IT 취준생이 CS 핵심 개념을 반복 학습할 수 있도록 돕는 플랫폼.

## 기술 스택

- Java 17 / Spring Boot 3.5 / Gradle
- JPA + PostgreSQL + Flyway (스키마 마이그레이션)
- Redis (캐싱, 메일 인증 코드)
- Spring Security + OAuth2 (Google, Kakao, Naver) + JWT
- springdoc-openapi (Swagger UI)
- Testcontainers (통합 테스트) / H2 (단위 테스트)
- Prometheus + Grafana + Loki (모니터링)
- GitHub Actions CI/CD → Docker Hub → 서버 배포

## 빌드 & 실행

```bash
# 빌드 (flyway validate 포함)
./gradlew build

# 테스트
./gradlew test

# 로컬 실행 (Docker Compose로 PostgreSQL, Redis 필요)
./gradlew bootRun
```

## 프로젝트 구조

```
src/main/java/gravit/code/
├── global/          # 공통 설정, 예외, 어노테이션, 필터, 이벤트
├── security/        # Spring Security 설정, JWT 필터
├── auth/            # OAuth 인증, 토큰 발급
├── user/            # 사용자 관리
├── chapter/         # 챕터 (최상위 학습 단위)
├── unit/            # 유닛 (챕터 하위)
├── lesson/          # 레슨 (유닛 하위)
├── problem/         # 문제
├── answer/          # 정답
├── learning/        # 학습 진행도, 연속 학습
├── bookmark/        # 문제 북마크
├── wrongAnsweredNote/ # 오답 노트
├── csnote/          # CS 노트
├── league/          # 리그
├── userLeague/      # 사용자별 리그 정보
├── userLeagueHistory/ # 리그 이력
├── season/          # 시즌 관리, 배치
├── mission/         # 미션
├── badge/           # 뱃지
├── friend/          # 팔로우/팔로잉
├── notice/          # 공지사항
├── report/          # 신고
├── admin/           # 관리자 기능
└── test/            # 테스트 데이터 초기화 (QA용)
```

## 아키텍처 규칙

### 레이어 구조
`Controller → Facade → Service → Repository` 순서를 따른다.
- **Controller**: HTTP 요청/응답 처리만 담당. `ChapterControllerDocs` 인터페이스를 implements하여 Swagger 분리
- **Facade** (`@Facade`): 여러 Service를 조합하는 비즈니스 로직. 커스텀 `@Facade` 어노테이션 사용
- **Service** (`@Service`): 단일 도메인의 비즈니스 로직. Query/Command 분리 시 `ChapterQueryService` 네이밍
- **Repository**: JPA Repository. Projection은 record DTO로 직접 반환

### 의존성 방향
- Controller → Facade (Facade가 있는 경우) 또는 Controller → Service
- Facade → 여러 Service
- Service → Repository
- **역방향 의존 금지**: Service가 Facade를, Repository가 Service를 참조하면 안 됨

### 코드 패턴
- **DTO**: `record` 사용. Response는 `private Builder` + `static create()` 팩토리 메서드 패턴
- **Entity**: `@NoArgsConstructor(access = PROTECTED)` + `private Builder` + `static create()` 팩토리 메서드
- **BaseEntity**: `createdAt`, `updatedAt` 자동 관리 (Asia/Seoul 타임존)
- **예외**: `throw new RestApiException(CustomErrorCode.XXX_NOT_FOUND)` 패턴
- **트랜잭션**: 조회 메서드는 `@Transactional(readOnly = true)`, 변경 메서드는 `@Transactional`

### API 문서 (Swagger)
- Controller마다 `controller/docs/` 하위에 `{Controller}Docs` 인터페이스 작성
- Controller는 이 인터페이스를 `implements`
- `@Tag`, `@Operation`, `@ApiResponses`를 Docs 인터페이스에 선언
- 에러 응답은 `ErrorResponse` 스키마 + `@ExampleObject`로 실제 에러코드 명시

### 데이터베이스
- Flyway 마이그레이션: `src/main/resources/db/migration/V{N}__{description}.sql`
- 빌드 시 `flywayValidate` 자동 실행
- 마이그레이션 파일은 한번 적용되면 절대 수정 금지

### 테스트
- 단위 테스트: `@ExtendWith(MockitoExtension.class)` + Mockito
- 통합 테스트: Testcontainers (PostgreSQL)
- Fixture 패턴으로 테스트 데이터 생성 (e.g., `LearningFixture.당일_학습_완료()`)
- 테스트 메서드명: 한글 서술형 (e.g., `연속학습일수_업데이트에_성공한다()`)
- 단위/통합 구분: `*UnitTest.java`, `*IntegrationTest.java`

## 주의사항

- `application.yml`의 시크릿은 환경변수로 주입. 절대 하드코딩 금지
- `@Facade`는 `gravit.code.global.annotation.Facade` 커스텀 어노테이션
- `LoginUser`는 `@AuthenticationPrincipal`로 주입받는 인증 객체
- 커밋 메시지는 한글로 작성 (e.g., `feat: 챕터 조회 API 구현`)