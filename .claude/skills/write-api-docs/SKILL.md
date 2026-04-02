---
name: write-api-docs
description: Swagger API 문서(ControllerDocs 인터페이스)를 작성할 때 사용. "API 문서 작성해줘", "Swagger 문서 만들어줘", "Docs 인터페이스 작성해줘" 등의 요청 시 자동 트리거.
allowed-tools: Read, Grep, Glob, Edit, Write
---

# API 문서 작성

대상 Controller: $ARGUMENTS

## 작성 절차

1. 대상 Controller 클래스를 읽어 모든 엔드포인트를 파악하라
2. 각 엔드포인트가 호출하는 Service/Facade 메서드를 추적하여 발생 가능한 예외를 수집하라
3. `CustomErrorCode`에서 해당 예외의 code와 message를 확인하라
4. `controller/docs/{Controller}Docs.java` 인터페이스를 생성하라
5. API 문서 템플릿은 [api-docs-template.md](api-docs-template.md)를 참조하라
6. Controller가 해당 Docs 인터페이스를 `implements` 하고 있는지 확인하라. 안 되어 있으면 추가하라

## 주의사항

- 에러코드 값은 `CustomErrorCode` enum과 반드시 일치시켜라
- 모든 API에 500 응답(`GLOBAL_5001`)을 포함하라
- JWT 필요 API는 description에 `"🔐 <strong>Jwt 필요</strong><br>"` 를 포함하라