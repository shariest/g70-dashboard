# Investigating Genesis G70 vehicle signals

[한국어](RESEARCH_ko.md) · [G70 Probe](../apps/g70probe/README.md)

The port starts with the 2019 Korean G70 Standard Gen5 head unit. The inherited dashboard has a JSN-specific decoder. Establish the G70 data source, frame structure and units before connecting any decoded value to the dashboard.

1. **Identify the unit.** Keep the Android product, device, SDK and incremental build from the probe together with the navigation software version.
2. **Inventory available interfaces.** The probe lists the OEM manager classes it can resolve and their public methods. A class being present does not establish that it delivers callbacks on this build.
3. **Capture the receive stream.** The probe opens the existing `hscan_mux` input and records raw bytes. Check the tagged log for connection, errors and byte count. A missing file or an earlier capture cannot establish reception in the current run.
4. **Examine the capture offline.** The scanner tests a candidate 264-byte container and returns entries including unknown function codes. Its built-in self-test uses synthetic data. Check framing against the capture, then compare candidate readings with an independent observation before assigning a label or unit.

The inherited HSCAN decoder checks an exact product and incremental build. Its JSN mappings and the archived installation records apply to the upstream vehicle. [Decoder](../apps/dashboard/src/com/github/shariest/g70/dashboard/HscanDataClient.java) · [Offline scanner](../apps/g70probe/tools/G70HscanScan.java) · [Current scope](STATUS.md)
