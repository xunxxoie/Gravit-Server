# Hooks

Claude Code가 특정 이벤트 발생 시 자동 실행하는 셸 스크립트들입니다.
`settings.json`의 `hooks` 블록에 등록되어 있어야 동작합니다.

| 파일 | 이벤트 | 설명 |
|---|---|---|
| `block-dangerous-commands.sh` | PreToolUse (Bash) | `rm -rf`, `drop database`, `git push --force` 등 위험 명령 차단 |
| `protect-migrations.sh` | PreToolUse (Edit/Write) | 기존 Flyway 마이그레이션 파일 수정/덮어쓰기 차단 |
| `notify-permission.sh` | Notification (permission_prompt) | 권한 승인 요청 시 OS 알림 |
| `notify-stop.sh` | Stop | 작업 완료 시 OS 알림 |

## OS별 알림 지원

`notify-permission.sh`, `notify-stop.sh`는 OS를 자동 판별하여 알림을 보냅니다.

- **macOS** — `osascript`
- **WSL (Windows)** — `powershell.exe` (BurntToast 또는 MessageBox)
- **Linux** — `notify-send`