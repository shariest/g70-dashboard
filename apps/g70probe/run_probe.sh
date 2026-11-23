#!/usr/bin/env bash
# Called by run-probe.sh. Also works directly with adb on PATH.
set -euo pipefail
cd "$(dirname "$0")"
command -v adb >/dev/null || { echo "저장소 루트에서 ./run-probe.sh 를 실행하세요." >&2; exit 1; }
# Keep Android paths intact for native adb.exe in Git Bash. Local paths below are relative.
export MSYS_NO_PATHCONV=1

adb devices -l
if ! serial=$(adb -d get-serialno | tr -d '\r') ||
   [[ -z "$serial" || "$serial" == unknown || "$(adb -s "$serial" get-state | tr -d '\r')" != device ]]; then
    echo "대상 헤드유닛 한 대를 USB로 연결하고 docs/ADB.md의 연결 상태를 확인하세요." >&2
    exit 1
fi
target=(adb -s "$serial")
"${target[@]}" install -r G70-Probe-debug.apk

capture=$(mktemp -d "capture-$(date +%Y%m%d-%H%M%S)-XXXXXX")
component=com.github.shariest.g70.probe/.ProbeActivity
result=$("${target[@]}" shell am start -S -W -n "$component" --es capture_id "$capture")
printf '%s\n' "$result"
if ! grep -Eq '^Status: ok[[:space:]]*$' <<< "$result"; then
    echo "G70 Probe 실행을 확인할 수 없습니다." >&2
    exit 1
fi

echo "정차 상태에서 Probe 화면을 열어 두세요. 수집을 마치거나 미연결 메시지가 표시되면 Enter를 누르세요."
read -r _
"${target[@]}" logcat -d -s 'G70PROBE:*' | awk -v marker="== $capture ==" 'index($0, marker) {found=1} found' > "$capture/g70_probe_log.txt"
if "${target[@]}" pull "/sdcard/g70-$capture.bin" "$capture/g70_hscan.bin.part" && [[ -s "$capture/g70_hscan.bin.part" ]]; then
    mv "$capture/g70_hscan.bin.part" "$capture/g70_hscan.bin"
else
    echo "이번 실행의 원시 데이터가 없습니다. g70_probe_log.txt에서 연결 상태를 확인하세요." >&2
fi
echo "수집 폴더: $(pwd)/$capture"
