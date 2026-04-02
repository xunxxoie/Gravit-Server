# Rules

Claude Code가 특정 파일 작업 시 조건부로 로딩하는 규칙 파일들입니다.

| 파일 | 적용 조건 | 설명 |
|---|---|---|
| `git-convention.md` | `**` | 커밋 메시지, 브랜치 전략, PR 규칙 |
| `common-code-convention.md` | `src/main/java/**/*.java` | 공통: 네이밍, 포맷팅, 예외 처리, 레이어 구조 |
| `layer-convention.md` | `**/controller/**`, `**/facade/**`, `**/service/**`, `**/repository/**` | 레이어별: Controller, Facade, Service, Repository 규칙 |
| `class-convention.md` | `**/domain/**`, `**/dto/**` | 클래스 타입별: Entity, DTO 작성 패턴 |
| `project-structure.md` | `src/main/java/**/*.java` | 프로젝트 디렉토리 구조, 도메인 패키지 내부 구조 |
| `database.md` | `db/migration/**`, `**/domain/*.java`, `**/repository/*.java` | Flyway 마이그레이션, JPA 매핑, SQL 스타일 |
| `security.md` | `**/security/**`, `**/auth/**`, `application*.yml` | 인증/인가, SecurityConfig, 시크릿 관리 |