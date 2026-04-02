---
description: Java 소스 코드를 작성하거나 수정할 때 공통으로 적용되는 컨벤션
paths:
  - "src/main/java/**/*.java"
---

# Common Code Convention

## 레이어 구조

Controller → Facade → Service → Repository 순서를 따른다.
역방향 의존을 만들지 마라: Service가 Facade를, Repository가 Service를 참조하면 안 된다.

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