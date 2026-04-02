---
name: write-test
description: 테스트 코드를 작성할 때 사용. "테스트 작성해줘", "테스트 코드 만들어줘", "이 클래스 테스트해줘" 등의 요청 시 자동 트리거.
allowed-tools: Read, Grep, Glob, Edit, Write
---

# 테스트 코드 작성

대상 클래스: $ARGUMENTS

## 작성 절차

1. 대상 클래스를 읽고 테스트할 메서드와 분기를 파악하라
2. 대상이 의존하는 클래스들을 확인하라
3. 기존 Fixture가 있는지 `src/test/java/gravit/code/{domain}/fixture/`를 확인하라
4. 없으면 Fixture부터 생성하라
5. 테스트 템플릿은 [test-code-template.md](test-code-template.md)를 참조하라
6. 단위 테스트와 통합 테스트 중 적절한 형태를 판단하라:
   - Service, Facade의 로직 검증 → 단위 테스트
   - DB 연동, 여러 레이어 통합 검증 → 통합 테스트
7. 테스트 메서드명은 한글 서술형으로 작성하라
8. 성공/실패/엣지 케이스를 모두 커버하라

## 주의사항

- `// given` / `// when` / `// then` 구간을 반드시 구분하라
- Entity의 id 설정은 `ReflectionTestUtils.setField()`를 사용하라
- 검증에는 AssertJ를 사용하라 (`assertThat`, `assertSoftly`, `assertThatThrownBy`)