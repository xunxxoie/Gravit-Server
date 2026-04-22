---
description: Controller, Facade, Service, Repository 레이어별 규칙
paths:
  - "src/main/java/**/controller/**/*.java"
  - "src/main/java/**/facade/**/*.java"
  - "src/main/java/**/service/**/*.java"
  - "src/main/java/**/repository/**/*.java"
---

# Layer Convention

## Controller

- `@RestController` + `@RequiredArgsConstructor`를 사용하라
- 기본 경로는 `@RequestMapping("/api/v1/{도메인복수형}")`으로 설정하라
- 반드시 `{Controller}Docs` 인터페이스를 implements 하라
- Controller에 비즈니스 로직을 넣지 마라. Facade 또는 Service에 위임만 하라
- Facade가 있으면 Facade를 주입하라. 없으면 Service를 직접 주입하라
- 인증된 사용자는 `@AuthenticationPrincipal LoginUser loginUser`로 주입받아라

## Facade

- 커스텀 어노테이션 `@Facade` + `@RequiredArgsConstructor`를 사용하라 (`gravit.code.global.annotation.Facade`)
- Facade는 여러 Service를 조합하는 비즈니스 로직을 담당한다
- 단일 Service 호출만 필요한 경우 Facade를 만들지 마라. Controller에서 Service를 직접 주입하라

## Service

- `@Service` + `@RequiredArgsConstructor`를 사용하라
- Service는 단일 도메인 로직만 담당하라. 다른 도메인 Service를 직접 호출하지 마라
- Query/Command를 분리할 때는 `{Domain}QueryService`, `{Domain}CommandService`로 네이밍하라
- 조회 메서드에는 `@Transactional(readOnly = true)`를, 변경 메서드에는 `@Transactional`을 붙여라

## Repository

- Repository는 `{domain}/repository/` 패키지에 위치시켜라
