---
description: Java 소스 코드를 작성하거나 수정할 때 적용되는 코드 컨벤션
paths:
  - "src/main/java/**/*.java"
---

# 코드 컨벤션

## 레이어 구조

Controller → Facade → Service → Repository 순서를 따른다.

- Controller에는 `@RestController` + `@RequiredArgsConstructor`를 사용하라
- Controller의 기본 경로는 `@RequestMapping("/api/v1/{도메인복수형}")`으로 설정하라
- Controller는 반드시 `{Controller}Docs` 인터페이스를 implements 하라
- Controller에 비즈니스 로직을 넣지 마라. Facade 또는 Service에 위임만 하라
- Facade가 있으면 Facade를 주입하라. 없으면 Service를 직접 주입하라
- 인증된 사용자는 `@AuthenticationPrincipal LoginUser loginUser`로 주입받아라
- Facade에는 커스텀 어노테이션 `@Facade` + `@RequiredArgsConstructor`를 사용하라 (`gravit.code.global.annotation.Facade`)
- Facade는 여러 Service를 조합하는 비즈니스 로직을 담당한다
- Service에는 `@Service` + `@RequiredArgsConstructor`를 사용하라
- Service는 단일 도메인 로직만 담당하라. 다른 도메인 Service를 직접 호출하지 마라
- Query/Command를 분리할 때는 `{Domain}QueryService`, `{Domain}CommandService`로 네이밍하라
- 조회 메서드에는 `@Transactional(readOnly = true)`를, 변경 메서드에는 `@Transactional`을 붙여라
- Repository는 `{domain}/repository/` 패키지에 위치시켜라
- 역방향 의존을 만들지 마라: Service가 Facade를, Repository가 Service를 참조하면 안 된다

## Entity

- `@Entity` + `@Getter` + `@NoArgsConstructor(access = AccessLevel.PROTECTED)`를 사용하라
- Builder는 private 생성자에 `@Builder`를 선언하라
- 객체 생성은 `static create()` 팩토리 메서드를 통해서만 하라 (내부에서 Builder 호출)
- 공통 필드가 필요하면 `BaseEntity`를 상속하라 (`createdAt`, `updatedAt` 자동 관리)
- 검증 로직은 Entity 내부 private 메서드로 구현하라
- 예외는 `throw new RestApiException(CustomErrorCode.XXX)` 패턴을 사용하라

## DTO

- DTO는 `record` 타입으로 선언하라
- Request에는 `@Schema` + validation 어노테이션(`@NotNull` 등)을 포함하라
- Response에는 `@Builder(access = AccessLevel.PRIVATE)` + `static create()` 또는 `static of()` 팩토리 메서드를 사용하라
- 패키지는 `{domain}/dto/request/`, `{domain}/dto/response/`로 분리하라

## 예외 처리

- `throw new RestApiException(CustomErrorCode.XXX)` 패턴을 사용하라
- 새 에러코드는 `CustomErrorCode` enum에 추가하되, 카테고리별 주석 그룹을 유지하라
- 에러코드 형식: `ERROR_NAME(HttpStatus.XXX, "DOMAIN_CODE", "한글 메시지")`

## 포맷팅

- 메서드 파라미터가 2개 이상이면 각 파라미터를 줄바꿈하여 작성하라
```java
public ReturnType methodName(
        String param1,
        String param2,
        String param3
) {
}
```

## 네이밍

- 패키지는 도메인 단위로 나눠라 (`chapter`, `user`, `bookmark`, `wrongAnsweredNote`)
- 클래스는 PascalCase로 작성하라 (`ChapterQueryService`, `BookmarkFacade`)
- 메서드는 camelCase + CRUD 동사를 사용하라 (`findById`, `addBookmark`, `deleteBookmark`)
- API 경로는 kebab-case 복수형으로 작성하라 (`/api/v1/chapters`, `/api/v1/bookmarks`)
