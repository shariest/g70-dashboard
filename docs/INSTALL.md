# G70 설치 안내

[English](INSTALL_EN.md) · [호환성](STATUS.md#한국어)

## 신호 수집용 Probe

저장소를 처음 받았다면 프로젝트 폴더로 이동하세요. 기존 체크아웃은 그 폴더에서 `git pull`로 갱신합니다. USB 디버깅이 켜진 헤드유닛 한 대를 정차 상태에서 노트북에 연결합니다. [USB 디버깅 설정](ADB.md)

- **Windows PowerShell·명령 프롬프트**에서 `.\run-probe.cmd`를 실행합니다. Git for Windows의 Bash를 사용합니다.
- **macOS·Linux·Git Bash**에서 `./run-probe.sh`를 실행합니다.

실행기가 기존 ADB를 찾고, 필요하면 첫 실행 때 Google에서 도구를 내려받습니다. 포함된 `apps/g70probe/G70-Probe-debug.apk`를 설치하고 실행하므로 노트북에서 APK를 빌드할 필요가 없습니다. G70 Probe 화면을 확인한 뒤 Enter를 누르면 터미널에 수집 폴더가 표시됩니다. [Probe 및 결과 파일](../apps/g70probe/README.md)

Android는 새 패키지 ID를 별도 앱으로 취급합니다. 이전 Probe가 설치된 기기에서도 실행기는 `com.github.shariest.g70.probe/.ProbeActivity`를 직접 엽니다.

## G70 계기판과 내 앱

두 앱은 소스에서 빌드할 수 있습니다. 빌드 결과는 미서명이며, G70에서 확인한 배포용 서명본과 설치 프로필은 아직 없습니다. 기존 원본 차종의 APK는 패키지 ID도 달라 G70 앱의 업데이트나 복구에 쓸 수 없습니다. [빌드 방법](BUILD.md) · [배포 이력](RELEASE.md)

G70 검증 시에는 APK 설치, 화면에서 내 앱 → G70 계기판 열기, 케이블 분리 후 재실행, 완전 재시동 뒤 바로가기 복원을 각각 기록합니다. [내 앱](MY_APPS_ko.md) · [바로가기](QUICK_ENTRY_ko.md)
