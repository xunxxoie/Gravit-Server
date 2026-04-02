#!/bin/bash
# PreToolUse hook: 위험한 Bash 명령 차단
# exit 0 = 허용, exit 2 = 차단

INPUT=$(cat)
COMMAND=$(echo "$INPUT" | jq -r '.tool_input.command // empty')

if [ -z "$COMMAND" ]; then
  exit 0
fi

BLOCKED_PATTERNS=(
  "rm -rf /"
  "rm -rf ~"
  "rm -rf ."
  "drop database"
  "drop table"
  "truncate table"
  "git push.*--force"
  "git reset --hard"
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