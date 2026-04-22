---
description: 보안, 인증/인가, Security 설정 관련 코드를 작성하거나 수정할 때 적용
paths:
  - "src/main/java/**/security/**/*.java"
  - "src/main/java/**/auth/**/*.java"
  - "src/main/resources/application*.yml"
---

# Security Rules

## 인증 구조

- JWT 기반 Stateless 인증을 사용한다 (`SessionCreationPolicy.STATELESS`)
- 인증 흐름: `JwtAuthFilter` → `AuthTokenProvider` → `SecurityContextHolder`
- 토큰은 `Authorization: Bearer {token}` 헤더로 전달된다
- 인증된 사용자는 `@AuthenticationPrincipal LoginUser loginUser`로 주입받아라
- `LoginUser`는 `OAuth2User` 구현체로 `id`, `provider`, `authorities`를 보유한다

## SecurityConfig 수정 시 주의

- 새 public 엔드포인트를 추가할 때 반드시 두 곳을 동시에 업데이트하라:
  1. `SecurityConfig.filterChain()` → `requestMatchers(...).permitAll()`
  2. `JwtAuthFilter.EXCLUDE_ENDPOINTS` → `HttpEndpoint.exact()` 또는 `HttpEndpoint.prefix()`
- Admin 전용 경로에는 `.hasRole("ADMIN")`을 사용하라

## 시크릿 관리

- JWT 시크릿, OAuth 클라이언트 키, DB 비밀번호 등은 `application.yml`에서 `${ENV_VAR}`로 환경변수 주입하라
- 코드나 설정 파일에 시크릿을 하드코딩하지 마라
- `.env`, `application-local.yml` 등 시크릿 포함 파일은 `.gitignore`에 등록하라

## 금지사항

- CORS 허용 도메인에 와일드카드(`*`)를 사용하지 마라
- 클라이언트가 보낸 userId를 신뢰하지 마라. `LoginUser.getId()`로 사용자를 식별하라