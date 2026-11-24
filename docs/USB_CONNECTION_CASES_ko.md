# G70과 현대·기아 5세대 내비의 USB 연결·APK 설치 사례

조사 기준일은 2026년 11월 24일이다. 국내 2019 G70에서는 차량 USB-A와 맥 USB-C를 직결했을 때 ADB 목록에 내비가 나타나지 않았다. 국내 벨로스터 N에는 유선 ADB 성공 기록이, 유럽 아이오닉에는 Wi-Fi ADB 설치 기록이, 프랑스 니로에는 USB 메모리의 APK를 직접 열어 설치한 기록이 있다. G70에서 같은 결과를 얻으려면 내비의 USB 역할과 실제로 열리는 메뉴부터 확인해야 한다. [G70 연결 안내](ADB.md) · [프로젝트의 실차 검증 범위](STATUS.md#한국어)

## 2019 G70과 맥에서 확인한 내용

| 확인 대상 | 관찰 및 근거 |
| --- | --- |
| 내비 모델과 소프트웨어 | 차주가 확인한 모델은 `IKBS.S5AMC.KR`이다. 제네시스는 이를 **G70 / 2019 포함, 표준형 5세대**로 분류한다. 공식 목록의 버전은 `IK19.KOR.SOP.V141.260114.STD_H`이며, 이번 차량에 설치된 전체 버전과 Android 빌드는 아직 읽지 못했다. [제네시스 공식 버전 목록](https://update.genesis.com/KR/KO/updateNoticeView/software-version) |
| 내비 화면 | 차주가 시도한 5회 터치는 반응이 없었고, 10회 터치 뒤에는 숫자 키패드와 `Input Password`, `Verify`가 표시됐다. 차주가 서비스 문의로 비밀번호를 해결했다. 이 화면에서 어느 모드가 열렸는지, USB 디버깅이 켜졌는지는 미확인이다. 연식별 공통 비밀번호를 뒷받침하는 G70 실차 기록도 찾지 못했다. [공식 SW 확인·단말기 문의 안내](https://update.genesis.com/KR/KO/cs/faq/2353) |
| 맥 USB 역할 | 차량 USB-A와 맥 USB-C를 직접 연결했다고 보고한 시점에, 맥의 활성 포트 하나에서 `DataRoleDescription = Device`가 관찰됐다. 이 포트가 차량에 연결된 포트라면 차량이 호스트 역할을 맡은 것이다. 이후 별도 조회에서는 활성 USB 2 전송이 보이지 않았다. |
| ADB와 설치 | 맥에서는 Google Platform-Tools 36.0.2를 프로젝트 캐시에서 실행했다. 확인한 시점마다 `adb devices -l` 목록은 비어 있었고, 맥 USB 장치 트리에서도 내비를 식별하지 못했다. 그 연결로 G70에 APK를 설치하거나 Probe를 실행한 기록은 없다. [프로젝트 실행기](../run-probe.sh) · [Google Platform-Tools](https://developer.android.com/tools/releases/platform-tools) |

북미 2019 G70 설명서는 미디어 재생용으로 앞쪽 멀티박스의 USB 포트를 지정하고, 센터 콘솔 수납함 포트가 있는 사양은 그 포트를 충전 전용으로 설명한다. 국내 차량의 진단용 포트는 별도 확인이 필요하다. 제네시스 국내 안내에 나온 문의처는 내비게이션 고객센터 1577-5159, 단말기 제조사 현대모비스 1588-7278, LG전자 1588-7386이다. [북미 G70 설명서 32쪽](https://owners.genesis.com/genesis/us/mygenesis/manuals/glovebox-manual/2019/g70/2019-G70-Multimedia-System-Manual.pdf#page=32) · [국내 공식 문의처](https://update.genesis.com/KR/KO/cs/faq/2353)

연결을 다시 확인할 때는 정차한 상태에서 같은 맥 포트의 케이블을 분리·재연결하며 아래 출력을 비교할 수 있다. 첫 줄은 USB 장치 트리, 둘째 줄은 이 맥에서 관찰한 USB 2 포트 상태, 마지막 줄은 연결된 Android 기기 목록이다. 캐시 경로는 저장소 루트 기준이다. 이미 ADB를 설치했다면 마지막 줄에서 `adb devices -l`을 사용해도 된다. [ADB 상태별 확인 절차](ADB.md)

```sh
ioreg -p IOUSB -w0
ioreg -r -c IOPortTransportStateUSB2 -w0 -l
./build/android-tools-darwin-36.0.2/platform-tools/adb devices -l
```

## 유선 ADB와 케이블

| 차량 또는 자료 | 케이블과 실제로 확인된 단계 |
| --- | --- |
| 국내 벨로스터 N 표준형 Gen5 | `JSNAS.S5AMC.KR` 연구 기록에 A–A 케이블과 어댑터를 사용한 이력이 있다. 케이블 구성을 바꾼 뒤 PC에서 `Telechips / DiPO`(VID `0x18d1`, PID `0xd00d`)가 보였고, ADB 접속과 앱 설치에 성공했다. 실차는 `jsn_kr`, Android 4.2.2 / API 17, `eng.vmadmin.20250902.081552`였다. 분석용 다운로드본 `JSN.KOR.SOP.V141.260114.STD_H`와 실차의 프레임워크 파일도 달랐다. 케이블 품번, 배선, 맥 쪽 커넥터는 공개 기록으로 특정할 수 없다. [벨로스터 N 케이블 기록][vn-cable] · [실차와 다운로드본 대조][vn-car] |
| 해외 벨로스터 N | 차주가 노트북과 **USB A–A**로 ADB를 연결했다고 썼다. 본인은 차량의 내비를 Gen5로 기억한다고 답했다. 별도로 국내 벨로스터 N에는 `JSNBS.S5BMC.KR` 표준형 Gen5W 사양도 있다. [해외 차주 기록](https://www.reddit.com/r/VelosterN/comments/1xoar14/hopping_on_the_train_of_custom_head_unit_software/) · [국내 사양 구분][vn-model] |
| 해외 Gen5W 개발자 기록 | 노트북과 내비 연결 준비물에 *male-to-male USB cable*이라고 적혀 있다. 양쪽이 수 플러그라는 설명만 있고, 컴퓨터 쪽이 Type-A인지 Type-C인지, 중간에 어댑터가 있는지는 명시하지 않았다. 국내외 공개 기록에서 **차량 A → 컴퓨터 C 직결로 Gen5W가 ADB 장치로 뜬 사례**, 또는 **맥의 C 어댑터 구성을 명시한 Gen5W ADB 성공 사례**는 찾지 못했다. [원문 장비 목록](https://github.com/cantcs/HKG_Gen5W_ReverseEngineer/blob/a05598e9cc76bb6f5ad08e506d3497aea7a1c1d9/README.md#L36-L39) |
| 국내 2019 G70 A–C 사용기 | MY19 차주가 동봉된 A–C 케이블로 메이튼 오토프로를 사용했다고 기록했다. 차량에 연결된 장치는 **CarPlay 동글**이었다. 기아 Gen5W 순정 설명서에도 USB 스마트폰 연결은 폰 프로젝션 용도로 안내된다. [G70 차주 기록](https://mayton.store/article/문의하기/6/92861/) · [기아 Gen5W 폰 프로젝션 설명서](https://ownersmanual.kia.com/ivi/STD_GEN5W/AVNT/KOR/Korean/002_Features_pprojection.html) |

일반 **USB-A 수–USB-C 수 케이블**은 규격상 A쪽에 기존 USB 호스트를 연결한다. 반대로 **맥 USB-C → C수–A암 어댑터**는 맥이 USB-A 장치를 받는 구성이다. 이 어댑터는 차량 내비의 역할을 바꾸지 않는다. 맥을 호스트로 쓰려면 내비가 디버그 장치로 동작하는 포트와 그 포트용 진단 케이블도 필요하다. G70에서 검증된 전체 구성이나 부품 번호는 미확인이다. [USB-IF Type-C 규격 32쪽](https://www.usb.org/sites/default/files/USB%20Type-C%20Spec%20R2.0%20-%20August%202019_0.pdf#page=32) · [Apple 어댑터 사양](https://support.apple.com/en-ae/111751)

**A–A라는 표기만으로 진단 케이블을 고를 수 없다.** USB-IF의 USB 3.1 Standard-A–Standard-A 규격은 양쪽 전원선(VBUS)과 USB 2.0 데이터선(D+/D−)을 서로 연결하지 않도록 명시한다. 두 호스트의 전원을 이어 버리는 케이블은 손상 위험이 있고, 해당 규격을 만족하는 케이블은 USB 2.0 데이터선을 필요로 하는 연결에 사용할 수 없다. [USB-IF 케이블 적합성 문서 20쪽](https://www.usb.org/sites/default/files/CabConn_Legacy_3_1_Compliance_Rev_1_1.pdf#page=20)

## 무선 ADB

- **독일 아이오닉 일렉트릭 28 kWh**의 SW `210111` 설치 기록에는 내비에서 `ADB Connect Type → Ethernet`을 선택한 뒤 **안드로이드폰과 내비를 같은 Wi-Fi 또는 핫스팟에 연결**해 ABRP Transmitter를 설치한 과정이 있다. 앱 개발자도 폰에서 내비의 IP를 지정하는 방법을 기록했다. 여기서 `Ethernet`은 메뉴 이름이고 실제 통신은 Wi-Fi였다. [독일 설치 기록](https://www.goingelectric.de/wiki/Hyundai-Ioniq-ABRP-Transmitter-Installation/) · [개발자 문서](https://github.com/g4rb4g3/ABRPTransmitterCompanion)
- **국내 벨로스터 N** 분석본에는 이더넷 ADB 탭의 표시 조건과 Wi-Fi ADB 문자열이 있었다. 프로젝트가 실차에서 확인한 최초 연결은 유선이었다. **G70**에서 Bluetooth 페어링만으로 ADB가 열린 사례, 또는 맥에서 USB 연결 없이 최초 Wi-Fi ADB 접속에 성공한 차량 기록은 찾지 못했다. Bluetooth PAN을 지원하더라도 IP 연결과 내비의 네트워크 ADB 활성화가 모두 필요하다. G70의 PAN 지원도 미확인이다. [벨로스터 N 무선 확인 범위][vn-wireless] · [Android 공식 ADB 문서][android-adb]
- Android 공식 문서에서 Android 11 이상은 Wi-Fi 페어링을 지원하며, 10 이하의 표준 TCP/IP 절차는 먼저 USB 연결을 사용한다. 아이오닉처럼 내비에서 네트워크 ADB를 켤 수 있고 기기의 IP·포트를 확인했다면 같은 네트워크에 있는 맥에서도 `adb connect <내비IP>:<포트>`와 `adb devices -l`로 확인할 수 있다. G70의 실제 Android 버전과 네트워크 ADB 메뉴는 미확인이다. [Android 공식 ADB 문서][android-adb]

## USB 메모리의 APK와 해외 차주 기록

| 차량 | 설치 결과와 조건 |
| --- | --- |
| 프랑스 기아 니로 PHEV | 2019년 2월 차주가 순정 브라우저에서 파일 관리자 앱을 먼저 설치했다. 이후 파일 관리자에서 USB 메모리에 복사한 APK를 직접 선택해 VLC와 YouTube가 내비에서 실행되는 것을 확인했다. 차주는 게시글의 저장매체 표기도 SD에서 **USB 메모리**로 정정했다. [프랑스 차주 원문](https://forums.automobile-propre.com/topic/applis-android-ajout%C3%A9es-%C3%A0-l%C3%A9cran-multim%C3%A9dia-13767/) |
| 유럽 현대 아이오닉 일렉트릭 | 2019년 독일 영상에는 내비 브라우저에서 USB 메모리 최상위의 APK를 선택해 설치하는 방법이 소개된다. 적용 조건으로 해당 내비가 외부 앱 설치를 허용하고 앱이 구형 Android와 호환되어야 한다고 설명한다. [아이오닉 차주 영상](https://www.youtube.com/watch?v=YFxBPEsE3IM) |
| 폴란드·미국·캐나다 사양 기아 스팅어 | 2019년 5월 폴란드 차주는 Android 4.2.2 순정 내비에 외부 앱을 설치했다고 보고했다. 같은 글에서 미국 버전 12.5 사용자는 해당 메뉴를 열 수 없었고, 캐나다 사양 GT2 사용자는 엔지니어링 모드에 들어갔으나 `Android Settings` 항목을 찾지 못했다. [스팅어 차주들의 원문](https://stingerforum.org/threads/install-almost-any-android-app-directly-on-media-tablet.12363/) |
| 이탈리아 기아 스포티지 | 2017년식 차주는 Waze와 Spotify APK를 설치하고 동시에 실행했다고 보고했다. 같은 글에서 2019년식 사용자는 `Android Settings`를 선택하면 시계 화면으로 돌아가 외부 앱 설치를 허용할 수 없었다고 했다. 기아도 유럽 Gen5.0 목록에서 QL 17MY와 QL PE를 다른 소프트웨어로 구분한다. [이탈리아 차주들의 원문](https://www.kiaclub.it/threads/sblocco-android-su-navigatore-kia-e-installazione-apps-senza-root.12485/page-2) · [기아 세대별 공식 자료](https://www.kia.com/content/dam/kwcms/kme/it/it/assets/contents/service/mobility-service/aggiornamento-mappe/Aggiornamento-Mappe-Luglio-2021.pdf) |
| 독일 현대 i30 | 순정 내비 장착을 검토하던 사용자가 자신의 소프트웨어 `PDE.EUR.SOP.V095.181008.DAU_A`와 함께 기존 안내로 ADB 설정에 들어갈 수 없었다고 기록했다. 해당 유닛에서 APK 설치까지 진행한 증거는 없다. [i30 사용자 원문](https://www.hyundaiboard.de/thread/38337-navigation-nachr%C3%BCsten-compact-gen5/) |

해외 사례와 대조할 때는 지역·모델 코드까지 확인한다. 현대 유럽 목록의 2017~2019 아이오닉 일렉트릭은 `AEAE.S5ALN.EU`, 기아 유럽 목록의 2018~2019 니로 PHEV는 `DEAP.S5ALN.EU`다. 등록 연식이 겹쳐도 국내 G70에서 같은 파일 관리자나 설치 화면을 제공한다고 단정할 수 없다. [현대 유럽 모델 목록](https://update.hyundai.com/EU/EN/updateNoticeView/software-version) · [기아 유럽 모델 목록](https://update.kia.com/EU/EN/updateNoticeView/software-version)

## 엔지니어링 모드의 USB Copy

| 차량 또는 자료 | 메뉴가 실제로 수행한 작업 |
| --- | --- |
| 국내 2022년형 기아 K5 | 연구진이 `USB Copy → Copy Logs to USB`로 내비의 시스템 로그를 USB 메모리에 저장했다. 발표자료 6쪽에서 실제 화면과 복사 방향 **차량 → USB**를 확인할 수 있다. [단국대 실차 화면](https://securesw.dankook.ac.kr/files/KSC2024_%EA%B9%80%EC%84%A0%EC%9E%AC_%EB%B0%9C%ED%91%9C%EC%9E%90%EB%A3%8C.pdf#page=6) |
| 북미 기아 AVN 5.0·5.0 Wide | 기아의 정비 문서는 `Copy image to USB`, `Copy all to usb` 및 USB 복사 완료 메시지를 진단 로그 수집 절차에서 설명한다. 수집한 파일은 USB로 꺼내 기술지원팀에 전달한다. [기아 정비자료 PS677](https://static.nhtsa.gov/odi/tsbs/2023/MC-10235104-0001.pdf#page=4) |
| 국내 2025 아반떼 CN7 PE / 아반떼 N 페이스리프트 | 차주들이 `USB Copy`에서 확인한 기능은 로그 복사, 로그 생성 시 화면 캡처, 화면 캡처 켜기·끄기였다. 두 보고 모두 별도 DSP 업데이트 항목이 없었다. [CN7 PE 차주 보고](https://github.com/xwtk/hmg_nemesis/issues/77) · [아반떼 N 차주 보고](https://github.com/xwtk/hmg_nemesis/issues/83) |
| 해외 기아 EV6 Gen5W | 일부 빌드에서 `USB Copy` 아래의 업데이트 기능을 이용해 별도 소프트웨어를 적용한 뒤 앱 관리자로 앱을 설치한 보고가 있다. 2022 EV6 FE 차주는 앱 실행 결과를 공개했다. 이 사례에서 일반 APK 하나를 USB 최상위에 놓고 순정 메뉴로 선택해 설치한 것은 확인되지 않았다. [EV6 차주의 실차 결과](https://www.reddit.com/r/KiaEV6/comments/1xjr0ug/manual_battery_preconditioning_at_work_using/) |

**G70에서 일반 APK에 해당하는 선례는 니로의 파일 관리자 방식이다.** 파일 관리자나 패키지 설치 화면이 USB의 APK를 열 수 있어야 하고, 내비가 해당 패키지의 설치를 허용해야 한다. `Copy Logs to USB`만 보인다면 그 버튼은 로그를 내보낸다. G70에서 USB 삽입만으로 APK를 자동 설치하거나 `USB Copy`에서 APK를 선택해 설치한 실차 기록은 찾지 못했다. 제네시스가 이 세대에 안내하는 SD 카드 절차는 내비 소프트웨어 업데이트용이다. [Android 외부 앱 배포 안내](https://developer.android.com/distribute/marketing-tools/alternative-distribution) · [제네시스 공식 업데이트 가이드](https://update.genesis.com/KR/KO/updateGuide/oKQl3A)

## G70 Probe에서 설치와 수집을 확인하는 기준

1. **내비에서 확인할 정보**는 모델명, 전체 SW 버전, 현재 모드와 하위 메뉴 이름이다. Android 개발자 옵션이 표시되면 USB 디버깅 상태를 확인할 수 있다. 기존 G70 문서가 조사한 `Diagnosis → Factory Test` 경로의 실제 스위치와 진단용 포트는 이 차량에서 검증 전이다. [G70 USB 디버깅 가이드](ADB.md)
2. **유선 설치·수집**은 맥의 `adb devices -l`에 내비 한 대가 `device`로 보인 뒤 저장소 루트에서 `./run-probe.sh`를 실행한다. 실행기는 `adb -d`로 USB 장치를 선택하고 디버그 서명된 Probe를 설치한다. 앱 실행 후 이번 세션의 로그와 수신한 원시 파일을 맥으로 가져온다. 네트워크 ADB만 연결된 상태에서는 이 실행기의 USB 선택 조건을 충족하지 않는다. [실행기 구현](../apps/g70probe/run_probe.sh) · [결과 파일 안내](../apps/g70probe/README.md)
3. **USB 메모리로 확인할 대상**은 저장소의 `G70-Probe-debug.apk`다. 차량에서 USB 파일 목록을 열 수 있다면 APK를 선택해 Android 설치 확인 창이 뜨는지, 설치 후 Probe 화면까지 열리는지를 따로 기록한다. 직접 실행한 Probe는 수신한 원시 데이터를 기본 공유 저장소의 `g70_hscan.bin`에 기록한다. 파일 관리자로 이 파일을 USB 메모리에 복사할 수 있는지는 별도 확인이 필요하며, 진단 로그는 `logcat`에 남는다. [Probe APK](../apps/g70probe/G70-Probe-debug.apk) · [저장 경로 구현](../apps/g70probe/src/com/github/shariest/g70/probe/Probe.java) · [설치 안내](INSTALL.md)

이 저장소가 제공하는 Probe는 API 17 대상의 디버그 서명 APK다. API 17은 앱의 빌드 정보이고, G70에 설치된 Android 버전은 실차 확인 대상이다. 계기판과 ‘내 앱’은 소스 빌드 단계이며 G70에서 설치·실행이 검증된 배포본은 아직 없다. [G70 호환성 현황](STATUS.md#한국어)

[vn-cable]: https://github.com/reikop/veloster-n-dashboard/blob/9f459f1566021aec33f938fc09d51d2aa2b6d866/docs/ADB.md#L74-L78
[vn-car]: https://github.com/reikop/veloster-n-dashboard/blob/9f459f1566021aec33f938fc09d51d2aa2b6d866/docs/RESEARCH_ko.md#L107-L113
[vn-model]: https://github.com/reikop/veloster-n-dashboard/blob/9f459f1566021aec33f938fc09d51d2aa2b6d866/docs/RESEARCH_ko.md#L36-L49
[vn-wireless]: https://github.com/reikop/veloster-n-dashboard/blob/9f459f1566021aec33f938fc09d51d2aa2b6d866/docs/ADB.md#L80-L84
[android-adb]: https://developer.android.com/tools/adb?hl=ko
