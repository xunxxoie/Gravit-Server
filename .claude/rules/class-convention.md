---
description: Entity, DTO 등 클래스 타입별 작성 패턴
paths:
  - "src/main/java/**/domain/**/*.java"
  - "src/main/java/**/dto/**/*.java"
---

# Class type Convention

## Entity

- `@Entity` + `@Getter` + `@NoArgsConstructor(access = AccessLevel.PROTECTED)`를 사용하라
- Builder는 private 생성자에 `@Builder`를 선언하라
- 객체 생성은 `static create()` 팩토리 메서드를 통해서만 하라 (내부에서 Builder 호출)
- 공통 필드가 필요하면 `BaseEntity`를 상속하라 (`createdAt`, `updatedAt` 자동 관리)
- 검증 로직은 Entity 내부 private 메서드로 구현하라
- 예외는 `throw new RestApiException(CustomErrorCode.XXX)` 패턴을 사용하라

## DTO

- DTO는 `record` 타입으로 선언하라
- Request에는 `@Schema` + validation 어노테이션을 포함하라
- Response에는 `@Builder(access = AccessLevel.PRIVATE)` + `static create()` 또는 `static of()` 팩토리 메서드를 사용하라
- 패키지는 `{domain}/dto/request/`, `{domain}/dto/response/`로 분리하라
