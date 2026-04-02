#!/bin/bash
# Stop hook: 작업 완료 알림

MSG="작업이 완료되었습니다."
TITLE="Claude Code"

OS=$(uname -s)
if [ "$OS" = "Darwin" ]; then
  osascript -e "display notification \"$MSG\" with title \"$TITLE\"" 2>/dev/null
elif grep -qi microsoft /proc/version 2>/dev/null; then
  powershell.exe -Command "[void](New-BurntToastNotification -Text '$TITLE','$MSG')" 2>/dev/null \
    || powershell.exe -Command "Add-Type -AssemblyName System.Windows.Forms; [System.Windows.Forms.MessageBox]::Show('$MSG','$TITLE')" 2>/dev/null
else
  notify-send "$TITLE" "$MSG" 2>/dev/null
fi

exit 0