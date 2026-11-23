# G70 순정 내비에서 USB 디버깅 켜기

[English](ADB_EN.md) · [Probe 설치](INSTALL.md)

G70 Probe를 노트북에서 설치하려면 순정 내비가 USB를 통해 ADB 연결을 받아야 합니다. 정차한 상태에서 진행하세요. 이미 노트북의 `adb devices -l` 목록에 헤드유닛 한 대가 `device`로 보이면 바로 Probe를 설치할 수 있습니다.

## 1. 내비 화면에서 소프트웨어 버전 확인

국내 표준형 5세대의 메뉴 경로는 **홈 → 전체메뉴 → 설정 → 일반 → SW정보/업데이트**입니다. 화면에 표시된 모델명과 소프트웨어 버전 전체를 기록하세요. 이 프로젝트가 조사한 대상은 **2019 G70**, 모델 표기 `IKBS.S5AMC.KR`, 버전 끝자리 `260114.STD_H`입니다. [제네시스 공식 버전 확인 안내](https://update.genesis.com/KR/KO/cs/faq/2353)

## 2. 내비에 표시되는 메뉴에 따라 USB 디버깅 켜기

| 내비에 보이는 화면 | 할 일 |
| --- | --- |
| Android **설정 → 개발자 옵션**이 있음 | **USB 디버깅**을 켭니다. 확인 창이 뜨면 내용을 확인하고 승인합니다. |
| 개발자 옵션이 숨겨져 있고 Android **기기 정보 → 빌드 번호**가 있음 | **빌드 번호**를 7번 누른 뒤 설정으로 돌아가 **개발자 옵션 → USB 디버깅**을 켭니다. 이 절차는 Android가 해당 항목을 노출하는 기기에서만 적용됩니다. |
| G70 순정 메뉴만 보임 | 이 프로젝트가 조사한 서비스 메뉴 경로는 **엔지니어링 모드 → Diagnosis → Factory Test**입니다. 대상 G70에서의 진입과 USB 디버깅 활성화는 실차 확인 전입니다. 일반 설정에 USB 디버깅이 없으면 1단계의 모델명·버전으로 단말기 제조사에 해당 버전의 USB 진단 연결 지원 여부와 진입 절차를 확인하세요. |

**빌드 번호**는 Android 기기 정보에 있는 항목입니다. 1단계의 **SW정보/업데이트** 화면은 내비 소프트웨어 버전을 확인하는 곳입니다. [Android 개발자 옵션 안내](https://developer.android.com/studio/debug/dev-options?hl=ko) · [제네시스 단말기 문의처](https://update.genesis.com/KR/KO/cs/faq/2353)

## 3. 노트북에서 연결 확인

내비와 노트북을 데이터 전송이 가능한 USB 포트와 케이블로 연결하세요. [Probe 실행기](INSTALL.md)가 ADB를 준비하고 기기 목록을 보여 줍니다. USB로 연결한 Android 기기는 대상 헤드유닛 한 대만 남겨 두세요. ADB가 이미 PATH에 있다면 아래 명령으로도 확인할 수 있습니다. [Platform-Tools 다운로드](https://developer.android.com/tools/releases/platform-tools)

```sh
adb devices -l
```

| 결과 | 다음 단계 |
| --- | --- |
| 헤드유닛 한 대가 `device` | 연결됐습니다. 프로젝트 폴더에서 Probe 설치를 진행하세요. |
| `unauthorized` | 내비 화면에 이 컴퓨터의 USB 디버깅 허용 창이 뜨면 승인하고 명령을 다시 실행하세요. |
| `offline` | 케이블을 다시 연결하고 목록을 확인하세요. |
| 목록이 비어 있음 | 노트북의 USB 장치 목록에 내비가 나타나는지 확인하세요. 나타나지 않으면 포트와 케이블을, 나타나면 내비의 USB 디버깅 상태를 확인하세요. Windows에서는 ADB용 USB 드라이버도 확인하세요. |

필요하면 아래 `SERIAL`을 목록에 나온 일련번호로 바꿔 실제 Android 빌드를 기록합니다. [Android의 USB 연결 확인 안내](https://developer.android.com/tools/adb?hl=ko)

```sh
adb -s SERIAL shell getprop ro.product.name
adb -s SERIAL shell getprop ro.build.version.sdk
adb -s SERIAL shell getprop ro.build.version.incremental
```

수집이 끝나면 결과 파일이 노트북에 저장됐는지 확인하세요. 이번에 Android 개발자 옵션에서 USB 디버깅을 켰다면 같은 스위치로 끌 수 있습니다. [Probe 설치와 수집](INSTALL.md)
