---
name: write-test
description: |
  테스트 코드를 작성한다.
  Trigger: "테스트 작성해줘", "테스트 코드 만들어줘", "이 클래스 테스트해줘", "XXXService 테스트", "테스트 추가해줘"
  Do NOT use for: 테스트 실행/결과 확인(→ run-test), 기존 테스트 수정만 필요한 경우(직접 Edit), 코드 리뷰
  Boundary: 테스트 대상 코드의 버그 수정은 이 스킬 범위 밖이다. 테스트 작성 중 버그를 발견하면 사용자에게 보고만 하라.
allowed-tools: Read, Grep, Glob, Edit, Write
---

# 테스트 코드 작성

대상 클래스: $ARGUMENTS

## Phase 1: 대상 분석

1. $ARGUMENTS로 지정된 클래스 파일을 Read로 읽어라
2. 클래스의 public 메서드 목록과 각 메서드의 분기(if/switch/예외)를 파악하라
3. 클래스가 의존하는 다른 클래스(생성자 파라미터, 필드 주입)를 목록화하라

> 다음 Phase 조건: 대상 클래스의 메서드와 의존성 목록이 파악되었을 때
> Skip 조건: 없음 (필수 Phase)

## Phase 2: 테스트 유형 결정

1. [references/test-classification.md](references/test-classification.md)를 읽어 분류 기준을 확인하라
2. Phase 1에서 파악한 의존성과 로직 특성에 따라 단위/통합 테스트를 결정하라
3. 결정 결과를 사용자에게 한 줄로 보고하라 (예: "BookmarkService → 단위 테스트로 작성합니다")

> 다음 Phase 조건: 테스트 유형이 결정되었을 때
> Skip 조건: 사용자가 "단위 테스트로 작성해줘" 또는 "통합 테스트로 작성해줘"처럼 유형을 직접 지정한 경우

## Phase 3: Fixture 확인 및 생성

1. `src/test/java/gravit/code/{domain}/fixture/` 디렉토리를 Glob으로 확인하라
2. 필요한 Entity의 Fixture가 이미 존재하면 해당 파일을 읽고 재사용하라
3. 존재하지 않으면 [references/test-code-template.md](references/test-code-template.md)의 Fixture 섹션을 참조하여 생성하라

> 다음 Phase 조건: 테스트에 필요한 모든 Fixture가 준비되었을 때
> Skip 조건: 대상 메서드가 Entity를 사용하지 않거나, 필요한 Fixture가 모두 이미 존재할 때

## Phase 4: 테스트 코드 작성

1. [references/test-code-template.md](references/test-code-template.md)에서 해당 테스트 유형의 템플릿을 읽어라
2. 패키지 구조는 `src/main/java`의 대상 클래스 경로를 미러링하라
3. Phase 1에서 파악한 각 메서드에 대해 다음 테스트 케이스를 작성하라:
   - 정상 동작 (성공 케이스)
   - 예외 발생 (실패 케이스: 존재하지 않음, 권한 없음, 중복 등)
   - 엣지 케이스 (경계값, 빈 리스트, null 등 — 해당하는 경우만)
4. 관련 테스트는 `@Nested` + `@DisplayName("한글 설명")` 으로 그룹화하라
5. 각 테스트 메서드에 `// given` / `// when` / `// then` 주석을 반드시 포함하라
6. 테스트 메서드명은 한글 서술형으로 작성하라 (예: `성공한다()`, `존재하지_않으면_예외를_던진다()`)
7. 검증에는 AssertJ를 사용하라 (`assertThat`, `assertSoftly`, `assertThatThrownBy`)
8. Entity의 id 설정은 `ReflectionTestUtils.setField()`를 사용하라

> 다음 Phase 조건: 테스트 파일 작성이 완료되었을 때
> Skip 조건: 없음 (필수 Phase)

## Phase 5: 검증

1. 작성한 테스트 파일이 컴파일 가능한지 import 누락, 타입 불일치를 점검하라
2. 누락된 테스트 케이스가 없는지 Phase 1의 분기 목록과 대조하라
3. 최종 결과를 사용자에게 보고하라: 작성된 파일 경로, 테스트 메서드 수, 커버한 분기

> Skip 조건: 없음 (필수 Phase)