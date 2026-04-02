# Agents

독립적인 컨텍스트 윈도우에서 실행되는 서브에이전트들입니다.
Claude가 자동으로 위임하거나, 사용자가 명시적으로 호출할 수 있습니다.

## 코드 리뷰

| 에이전트 | 관점 | 점검 항목 |
|---|---|---|
| `performance-reviewer` | 성능 | N+1 쿼리, 불필요한 DB 호출, 트랜잭션 범위, 인덱스 누락 |
| `logic-reviewer` | 로직 | 의도와 구현의 정합성, Controller→Facade→Service 흐름 추적, 엣지 케이스 누락 |
| `security-reviewer` | 보안 | 인증/인가 우회, 시크릿 노출, SQL Injection, 입력 검증 |

## Git 작업

| 에이전트 | 설명 |
|---|---|
| `gh-commit-agent` | 변경 사항 분석 → 커밋 메시지 생성 (`{type}: 내용(#이슈번호)`) |
| `gh-pr-agent` | 브랜치 diff 분석 → PR 생성 (base: `dev`, 팀 PR 템플릿 적용) |

## 브랜치 구조

```
main → dev → 각자 개발 브랜치
```

- `main`, `dev`에 직접 커밋하지 않는다
- PR의 base 브랜치는 기본적으로 `dev`이다