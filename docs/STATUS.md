# Genesis G70 compatibility and verification

The development target is the Korean-market **2019 Genesis G70, Standard Gen5**, identified in the probe notes as `IKBS.S5AMC.KR` with software ending in `260114.STD_H`. These are navigation software identifiers. The Android product name, incremental build and available OEM services must be read from the target head unit. [G70 Probe](../apps/g70probe/README.md)

| Component | Available evidence | Still to verify on G70 |
| --- | --- | --- |
| G70 Dashboard | API 17 source build, synthetic telemetry and renderer tests | Installation, OEM callbacks, units, signal mapping and screen behavior |
| G70 My Apps | API 17 source build, shortcut service and receiver tests on the host | Visible entry from HOME, permissions and shortcut restoration after a full restart |
| G70 Probe | Version 1.0 debug APK, package manifest and file hash | Device acceptance of its signature, manager availability and readable HSCAN frames |

The dashboard requires the factory `automotive` library. Its inherited HSCAN decoder is gated to the upstream product and firmware. The G70 probe reads raw bytes independently of that decoder. There is no verified G70 profile for the dashboard installer or factory-menu tools. [Limitations](LIMITATIONS.md) · [Artifact inventory](../release.json)

## 한국어

개발 대상은 국내 **2019년형 G70 표준형 5세대** 내비게이션입니다. 소프트웨어 표기 `IKBS.S5AMC.KR` 및 `260114.STD_H`만으로 Android의 product·incremental 값이나 차량 신호의 의미를 확정할 수 없습니다.

계기판과 내 앱은 API 17 대상 소스 빌드와 PC 테스트를 확인했습니다. G70 Probe의 디버그 APK를 제공하며, G70에서의 설치·신호 수신·화면 표시·재시동 복원은 아직 검증 전입니다. 원본 차종에서 확인한 설치·주행 결과는 배포 이력에서 확인할 수 있습니다. [배포 이력](RELEASE.md)
