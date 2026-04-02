#!/bin/bash
# PreToolUse hook: 위험한 Bash 명령 차단
# settings.json의 deny와 중복되는 패턴은 제외 (deny가 먼저 차단하므로)
# 이 hook은 deny에서 커버하지 못하는 추가 패턴을 차단한다
# exit 0 = 허용, exit 2 = 차단

INPUT=$(cat)
COMMAND=$(echo "$INPUT" | jq -r '.tool_input.command // empty')

if [ -z "$COMMAND" ]; then
  exit 0
fi

BLOCKED_PATTERNS=(
  "drop database"
  "drop table"
  "truncate table"
  "chmod -R 777"
  "> /dev/sda"
  "mkfs\."
  ":(){ :|:& };:"
)

COMMAND_LOWER=$(echo "$COMMAND" | tr '[:upper:]' '[:lower:]')

for pattern in "${BLOCKED_PATTERNS[@]}"; do
  if echo "$COMMAND_LOWER" | grep -qE "$pattern"; then
    echo "차단됨: '$pattern' 패턴에 매칭되는 위험한 명령입니다." >&2
    exit 2
  fi
done

exit 0
