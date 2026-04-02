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
./gradlew build      # 빌드 (flyway validate 포함)
./gradlew test       # 테스트
./gradlew bootRun    # 로컬 실행 (Docker Compose로 PostgreSQL, Redis 필요)
```

## 프로젝트 구조

```
src/main/java/gravit/code/
├── global/            # 공통 설정, 예외, 어노테이션, 필터, 이벤트
├── security/          # Spring Security 설정, JWT 필터
├── auth/              # OAuth 인증, 토큰 발급
├── user/              # 사용자 관리
├── chapter/           # 챕터 (최상위 학습 단위)
├── unit/              # 유닛 (챕터 하위)
├── lesson/            # 레슨 (유닛 하위)
├── problem/           # 문제
├── answer/            # 정답
├── learning/          # 학습 진행도, 연속 학습
├── bookmark/          # 문제 북마크
├── wrongAnsweredNote/ # 오답 노트
├── csnote/            # CS 노트
├── league/            # 리그
├── userLeague/        # 사용자별 리그 정보
├── userLeagueHistory/ # 리그 이력
├── season/            # 시즌 관리, 배치
├── mission/           # 미션
├── badge/             # 뱃지
├── friend/            # 팔로우/팔로잉
├── notice/            # 공지사항
├── report/            # 신고
├── admin/             # 관리자 기능
└── test/              # 테스트 데이터 초기화 (QA용)
```

## 핵심 규칙 요약

- 레이어: Controller → Facade → Service → Repository (세부 규칙은 `.claude/rules/` 참조)
- 커밋 메시지: `{type}: 커밋 내용(#{이슈번호})` (type: feat, hotfix, fix, docs, test, cicd, refactor)
- 브랜치: `main` → `dev` → 각자 개발 브랜치. main/dev에 직접 커밋하지 마라
- 시크릿은 `application.yml`에서 `${ENV_VAR}`로 환경변수 주입. 절대 하드코딩 금지