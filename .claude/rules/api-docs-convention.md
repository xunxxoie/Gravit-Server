---
description: Swagger API 문서(ControllerDocs 인터페이스)를 작성하거나 수정할 때 적용
paths:
  - "src/main/java/**/controller/docs/*Docs.java"
---

# API Docs Convention

## 파일 구조

- 각 Controller마다 `controller/docs/{Controller}Docs.java` 인터페이스를 생성하라
- Controller는 이 인터페이스를 `implements` 하라
- Swagger 어노테이션은 Docs 인터페이스에만 선언하라. Controller에는 붙이지 마라

## 어노테이션 규칙

- `@Tag`의 name은 `"{Domain} API"` 형식으로 작성하라 (e.g., `"Friend API"`, `"User API"`)
- `@Operation`의 summary는 기능을 한 줄로 요약하라 (e.g., `"팔로잉"`, `"유저 정보 조회"`)
- JWT가 필요한 API는 description에 `"🔐 <strong>Jwt 필요</strong><br>"` 를 포함하라
- 성공 응답 description은 `"✅ {성공 메시지}"`, 실패는 `"🚨 {에러 설명}"` 형식으로 작성하라
- 모든 API에 500 응답(`GLOBAL_5001`)을 포함하라
- 에러 응답에는 `schema = @Schema(implementation = ErrorResponse.class)`를 사용하라
- `@ExampleObject`의 value에는 `CustomErrorCode`의 실제 code와 message를 그대로 넣어라
- PathVariable, RequestParam에는 `@Parameter(description = "...")`를 추가하라
- 인증 파라미터 `@AuthenticationPrincipal LoginUser loginUser`는 Docs 인터페이스에도 선언하라

## DTO @Schema

- Request/Response record의 각 필드에 `@Schema`를 붙여라
- `@Schema`의 속성이 2개 이상이면 줄바꿈하라:
```java
@Schema(
        description = "문제 아이디",
        example = "1"
)
```

## 참조

- `ErrorResponse`는 `gravit.code.global.exception.domain.ErrorResponse`를 사용하라
- 에러코드 값은 `CustomErrorCode` enum과 반드시 일치시켜라