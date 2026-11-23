# 제네시스 G70 대시보드의 출처와 권리

이 프로젝트는 Veloster N Dashboard를 포크해 제네시스 G70용으로 개발합니다. 프로젝트 자체의 Java·Python 코드와 문서는 MIT 라이선스로 배포하며 원 저작권 고지를 보존합니다. [라이선스](LICENSE) · [원본 프로젝트](https://github.com/reikop/veloster-n-dashboard)

G70 계기판·내 앱·Probe와 공용 Java 코드는 `com.github.shariest.g70` 네임스페이스를 사용합니다. `compile-stubs/`의 `com.hkmc` 클래스는 차량의 OEM 인터페이스로 컴파일하기 위한 최소 선언입니다. Android, OEM 프레임워크와 SDK 도구의 권리는 각 권리자에게 있습니다.

현재 포함된 G70 Probe APK에는 Android 디버그 인증서 서명이 있습니다. 원본 프로젝트에서 배포한 APK는 공개 AOSP 플랫폼 테스트 인증서로 서명된 별도 산출물입니다. 해당 인증서는 OTA 서명 권한을 의미하지 않습니다. [파일 및 서명 기록](docs/RELEASE.md)

원본 연구에서 사용한 외부 도구 nudl의 라이선스는 GPL-3.0-only입니다. 도구, 제조사 펌웨어 및 외부 데이터베이스는 각 권리자의 조건을 따릅니다. 순정 메뉴 연구의 helper와 컴파일 선언은 프로젝트 코드이며 OEM 원본을 이용해 생성한 파일에는 제조사 코드가 포함됩니다. 제조사 APK·ODEX·펌웨어·개인 서명키는 배포하지 않습니다.

Genesis, G70, Hyundai, Veloster N, Android, CarPlay 등은 해당 권리자의 명칭입니다. 이 프로젝트는 독립 개발 프로젝트입니다.
