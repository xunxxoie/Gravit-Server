# Rules

Claude Code가 특정 파일 작업 시 조건부로 로딩하는 규칙 파일들입니다.

| 파일 | 적용 조건 | 설명 |
|---|---|---|
| `common-code-convention.md` | `src/main/java/**/*.java` | 레이어 구조, Entity/DTO 패턴, 네이밍, 포맷팅 |
| `test-code-convention.md` | `src/test/java/**/*.java` | 단위/통합 테스트, Fixture 패턴 |
| `api-docs-convention.md` | `**/controller/docs/*Docs.java` | Swagger ControllerDocs 인터페이스 작성 규칙 |
| `database.md` | `db/migration/**`, `**/domain/*.java`, `**/repository/*.java` | Flyway 마이그레이션, JPA 매핑, SQL 스타일 |
| `security.md` | `**/security/**`, `**/auth/**`, `application*.yml` | 인증/인가, SecurityConfig, 시크릿 관리 |