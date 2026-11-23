# G70 Probe 사용법

국내 2019년형 제네시스 G70 표준형 5세대의 Android 빌드, OEM 매니저, 원시 차량 신호를 조사하는 앱입니다. 패키지는 `com.github.shariest.g70.probe`입니다. 저장소에 API 17용 디버그 서명 APK를 포함합니다. [차량·펌웨어 확인 범위](../../docs/STATUS.md#한국어)

## 노트북과 차량 연결

1. 정차한 G70에서 USB 디버깅을 확인하고 노트북에 헤드유닛 한 대를 USB로 연결합니다. [USB 디버깅 안내](../../docs/ADB.md)
2. 노트북에 `git clone https://github.com/shariest/g70-dashboard.git`로 받아 `g70-dashboard` 폴더로 이동합니다. 이미 받은 경우에는 그 폴더에서 `git pull`을 실행합니다.
3. Windows PowerShell·명령 프롬프트에서는 `.\run-probe.cmd`, macOS·Linux·Git Bash에서는 `./run-probe.sh`를 실행합니다. Windows용 실행기는 Git for Windows를 사용합니다.
4. 차량에 G70 Probe 화면이 열리면 그대로 두세요. **캡처 완료** 또는 **hscan 미연결**이 표시되면 노트북에서 Enter를 눌러 이번 세션의 결과를 가져옵니다.

실행기는 PATH나 Android SDK에 설치된 ADB를 우선 사용합니다. ADB가 없으면 Google의 Platform-Tools 36.0.2를 내려받아 SHA-256을 대조하고 저장소의 `build/`에 보관합니다. 이 경우 첫 실행에는 인터넷 연결과 curl, ZIP 압축 해제 도구가 필요합니다. 노트북에 미리 한 번 실행해 받아 두면 차량에서는 캐시를 사용합니다. [Google Platform-Tools](https://developer.android.com/tools/releases/platform-tools)

## 결과 파일

터미널에 표시되는 `apps/g70probe/capture-날짜-시간-식별자/` 폴더에서 다음 파일을 확인합니다.

- `g70_probe_log.txt`에는 이번 Probe 실행의 빌드 정보, 매니저 목록, HSCAN 연결 상태가 들어갑니다.
- `g70_hscan.bin`은 이번 실행에서 수신한 원시 바이트입니다. 최대 크기는 256 KiB입니다. 신호를 받지 못했다면 로그에서 연결 상태를 확인하세요.

G70 헤드유닛이 디버그 서명 설치를 허용하는지와 이 앱에서 `hscan_mux`에 접근할 수 있는지는 실차 확인 항목입니다. 설치나 실행이 실패하면 터미널에 표시된 오류를 확인하세요.

## 개발 PC에서 재빌드

```sh
./apps/g70probe/build.sh
```

빌드에는 Android SDK, JDK 및 디버그 서명키가 필요합니다. 프레임 스캐너의 합성 데이터 자체 검사는 PC에서 실행합니다. [스캐너 소스](tools/G70HscanScan.java) · [G70 신호 조사 과정](../../docs/RESEARCH_ko.md)
