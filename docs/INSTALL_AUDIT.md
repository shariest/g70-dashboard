# G70 installation checks and the upstream audit

The October 2026 upstream audit found that installing a launcher APK and obtaining a visible entry on the factory HOME screen were separate events. The original vehicle had a separate OEM menu modification. Later, the owner confirmed reopening My Apps from an edge button on that vehicle. [Archived evidence](../archive/upstream/VALIDATION.json)

For the G70 port, the probe, dashboard and launcher have independent Android identities. A probe installation alone provides no evidence that the dashboard's required OEM library is available. Record the following for a future G70 dashboard/launcher installation.

| Check | Evidence |
| --- | --- |
| Device and package identity | Actual product, SDK and incremental build, installed package, version and APK checksum |
| Launch | The expected activity appears on the parked vehicle's screen |
| Return from HOME | The saved edge button or notification reopens G70 My Apps and its dashboard button opens G70 Dashboard |
| Full restart | The opted-in shortcut can be used after a separate full power cycle |

## 한국어

원본 차량에서는 설치 성공과 순정 홈에서 앱을 다시 여는 경로를 따로 확인했습니다. G70에서도 Probe 설치, 계기판 화면 표시, HOME에서 재실행, 완전 재시동 뒤 바로가기 복원을 개별 결과로 기록합니다. [G70 설치 상태](STATUS.md#한국어)
