# Source changes inherited by the G70 port

The upstream project reviewed NEMESIS in November 2026. The G70 fork inherits the following application-level changes. They are exercised by host tests. Delivery of the corresponding OEM events on a G70 still needs vehicle evidence.

| Area | Behavior in the G70 sources |
| --- | --- |
| Shortcut visibility | The service tracks standard Android and OEM screen events. QuickBoot restoration depends on the saved shortcut mode. [Service](../apps/hub/src/com/github/shariest/g70/hub/QuickEntryService.java) · [Receiver](../apps/hub/src/com/github/shariest/g70/hub/QuickEntryReceiver.java) |
| Installation receipts | The installer flushes the receipt before replacement and syncs the parent directory on POSIX hosts. [Installer journal](../scripts/menu_entry.py) |
| Subscription recovery | Missing registrations are retried with backoff. Old-session callbacks are rejected after a restart. [VehicleDataClient](../apps/dashboard/src/com/github/shariest/g70/dashboard/VehicleDataClient.java) |
| Error records | Each app keeps two bounded private files and forwards uncaught Java exceptions to the previous handler. [Diagnostics](DIAGNOSTICS.md) |

The original review and host-test counts belong to the upstream source revision. [Initial assessment](../archive/upstream/docs/validation/nemesis-adoption-20261102.json) · [Follow-up assessment](../archive/upstream/docs/validation/nemesis-deep-review-20261102.json)

## 한국어

G70 포크는 원본의 화면 상태 처리, 바로가기 복원, 설치 기록 저장, 차량 구독 재시도와 오류 기록 코드를 이어받았습니다. G70의 실제 화면 꺼짐·켜짐, QuickBoot 방송과 전원 종료 뒤 동작은 실차에서 확인할 항목입니다. [현재 확인 범위](STATUS.md#한국어)
