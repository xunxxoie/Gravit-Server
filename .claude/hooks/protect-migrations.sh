#!/bin/bash
# PreToolUse hook: 기존 Flyway 마이그레이션 파일 수정 차단
# 새 마이그레이션 파일 생성(Write)은 허용, 기존 파일 수정(Edit)은 차단
# exit 0 = 허용, exit 2 = 차단

INPUT=$(cat)
TOOL_NAME=$(echo "$INPUT" | jq -r '.tool_name // empty')
FILE_PATH=$(echo "$INPUT" | jq -r '.tool_input.file_path // empty')

if [ -z "$FILE_PATH" ]; then
  exit 0
fi

# 마이그레이션 파일이 아니면 허용
if ! echo "$FILE_PATH" | grep -q "db/migration/V.*\.sql"; then
  exit 0
fi

# Edit(수정)이면 차단, Write(새 파일 생성)이면 허용
if [ "$TOOL_NAME" = "Edit" ]; then
  echo "차단됨: 기존 Flyway 마이그레이션 파일은 수정할 수 없습니다. 새 버전 파일을 추가하세요." >&2
  exit 2
fi

# Write인데 파일이 이미 존재하면 차단
if [ "$TOOL_NAME" = "Write" ] && [ -f "$FILE_PATH" ]; then
  echo "차단됨: 기존 Flyway 마이그레이션 파일을 덮어쓸 수 없습니다. 새 버전 파일을 추가하세요." >&2
  exit 2
fi

exit 0