# G70 artifacts and upstream release history

| Component | Package | Artifact in this fork |
| --- | --- | --- |
| G70 Probe 1.0 | `com.github.shariest.g70.probe` | [Debug APK](../apps/g70probe/G70-Probe-debug.apk) |
| G70 Dashboard | `com.github.shariest.g70.dashboard` | Source build, unsigned |
| G70 My Apps | `com.github.shariest.g70.hub` | Source build, unsigned |

The probe uses an Android debug certificate. Its package, version, byte count and SHA-256 are recorded in the manifest. Run `python3 scripts/verify_release.py` from the repository root to check the bundled file. [Artifact manifest](../release.json) · [Checksums](../SHA256SUMS) · [Vehicle verification status](STATUS.md)

## Upstream provenance

The Veloster N project released N Dashboard v6 and My Apps v10 in `v2026.10.26-i18n`. The October 27 install and readback checks concerned those signed files on the Korean JSN head unit. The original APKs, hashes, metadata and target profile are under `archive/upstream/`. Paths inside those historical records are relative to the original project tree. [Upstream release manifest](../archive/upstream/release.json) · [Original validation](../archive/upstream/VALIDATION.json)

## 한국어

G70용으로 저장소에 포함된 APK는 신호 조사용 Probe 1.0입니다. 계기판과 내 앱은 미서명 소스 빌드 단계입니다. 원본 프로젝트의 서명·설치 이력과 예전 버전은 원래 패키지명 및 해시로 보관합니다. [G70 설치 안내](INSTALL.md)
