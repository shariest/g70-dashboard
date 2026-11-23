# G70 media artwork and upstream AVRCP research

The G70 dashboard can draw artwork supplied by a media source. Receiving Bluetooth artwork through AVRCP Cover Art is a separate transport feature. The upstream private experiment stopped at Bluetooth service discovery. Image transfer and end-to-end display over that transport were unconfirmed.

The G70 fork includes the dashboard media UI and artwork loader. There is no G70 capture establishing Cover Art service discovery, transfer or persistence. The private transport and OEM trial builds are outside the repository. [Artwork loader](../shared/src/com/github/shariest/g70/vehicle/ArtworkLoader.java) · [G70 limitations](LIMITATIONS.md)

## 한국어

G70 계기판은 미디어 소스가 넘긴 앨범아트를 표시할 수 있습니다. Bluetooth Cover Art를 직접 수신하는 실험은 원본 차종에서 서비스 탐색 단계까지 진행됐습니다. G70에서 쓸 수 있는 수신 기능으로 검증된 상태는 아닙니다. [실험 목록](../experimental/README.md)
