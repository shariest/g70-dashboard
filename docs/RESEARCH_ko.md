# 제네시스 G70 차량 신호 조사

[English](RESEARCH.md) · [G70 Probe 사용법](../apps/g70probe/README.md)

첫 대상은 국내 2019년형 G70 표준형 5세대 내비게이션입니다. 기존 계기판의 추가 데이터 해석기는 JSN 펌웨어에서 확인한 형식을 사용합니다. G70에서는 먼저 수신 경로·프레임 구조·단위를 확인해야 합니다.

1. **헤드유닛 식별.** 내비 소프트웨어 버전과 Probe가 기록한 Android product, device, SDK, incremental을 함께 보관합니다.
2. **OEM 인터페이스 확인.** Probe는 기기에서 찾은 매니저와 메서드를 기록합니다. 클래스 존재 여부와 실제 콜백 수신 여부는 각각 확인합니다.
3. **원시 수신 기록.** Probe는 기존 `hscan_mux` 입력에서 바이트를 읽습니다. 이번 실행의 로그에서 연결 여부, 오류, 바이트 수를 확인합니다. 이전에 남은 파일만으로 새로 수신했다고 판단할 수 없습니다.
4. **PC에서 해석.** 스캐너는 264바이트 컨테이너 가정을 검사하고 알 수 없는 함수 코드도 반환합니다. 내장 테스트는 합성 데이터입니다. 실차 파일로 프레임 경계를 확인하고, 관찰한 값과 대조한 뒤 표시 항목과 단위를 연결합니다.

기존 HSCAN 디코더는 원본 차종의 product와 incremental이 모두 맞을 때만 동작합니다. G70 데이터 경로를 조사할 때는 별도 Probe를 사용합니다. [디코더](../apps/dashboard/src/com/github/shariest/g70/dashboard/HscanDataClient.java) · [스캐너](../apps/g70probe/tools/G70HscanScan.java) · [확인 범위](STATUS.md#한국어)
