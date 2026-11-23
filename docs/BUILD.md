# G70 계기판과 내 앱 빌드

프로젝트 루트에서 아래 명령을 실행합니다. 두 앱은 Java 7 언어 수준, min/target SDK 17, DEX 035를 사용합니다. `compile-stubs/`의 OEM API 선언은 계기판 컴파일에만 사용합니다. [앱별 설치 상태](INSTALL.md)

## 준비물

- Python 3.9 이상, JDK 17의 `java`와 `javac`
- Android API 16 또는 17의 `android.jar`
- Android SDK Build Tools의 `aapt2`와 D8를 포함하는 JAR

## 호스트 테스트

```sh
export JAVA_HOME=/path/to/jdk17
python3 scripts/test.py --java "$JAVA_HOME/bin/java"
python3 -m unittest discover -s tests -p 'test_*.py'
python3 scripts/generate_translations.py --check
python3 scripts/verify_release.py
```

Java와 Python 테스트는 합성 데이터로 수신·만료 처리, 바로가기 수명주기, 화면 그리기 등을 확인합니다. 마지막 명령은 저장소에 포함된 G70 Probe APK의 무결성을 검사합니다.

## 미서명 APK

아래 예시에서 도구 경로를 개발 PC에 맞게 지정합니다. 빌더는 기존 출력 폴더를 덮어쓰지 않습니다. 같은 앱을 다시 빌드할 때는 새 `--out` 경로를 사용합니다.

```sh
android_jar=/path/to/android.jar
build_tools=/path/to/android-sdk/build-tools/VERSION

for app in dashboard hub; do
  python3 scripts/build.py "$app" \
    --java "$JAVA_HOME/bin/java" --android-jar "$android_jar" \
    --r8-jar "$build_tools/lib/d8.jar" --aapt2 "$build_tools/aapt2" \
    --out "build/$app"
done
```

| 앱 | 결과 파일 | 패키지 |
| --- | --- | --- |
| 계기판 | `build/dashboard/dashboard-UNSIGNED.apk` | `com.github.shariest.g70.dashboard` |
| 내 앱 | `build/hub/hub-UNSIGNED.apk` | `com.github.shariest.g70.hub` |

API 17 설치에는 호환되는 V1 서명이 필요합니다. G70 헤드유닛의 서명 수용 여부와 순정 `automotive` 라이브러리 제공 여부는 실차 확인 항목입니다. Probe는 자체 빌드 스크립트로 디버그 서명합니다. [Probe 빌드](../apps/g70probe/README.md) · [확인 범위](STATUS.md#한국어)

번역은 JSON을 수정한 뒤 Java 카탈로그를 다시 생성합니다. 화면 미리보기는 실제 렌더러를 호스트에서 실행해 만들 수 있습니다. [번역 작업](../translations/README.md) · [퍼포먼스 미리보기](PERFORMANCE.md)
