#!/usr/bin/env python3
"""Run synthetic host tests without Android, private logs, network or ADB."""
import argparse
import os
from pathlib import Path
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[1]


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--java", default="java")
    args = parser.parse_args()
    found = shutil.which(args.java)
    if not found:
        raise SystemExit("JDK 17 java is required")
    java = Path(found).resolve()
    javac = java.with_name("javac.exe" if os.name == "nt" else "javac")
    hub = ROOT / "apps/hub/src/com/github/shariest/g70/hub"
    sources = [ROOT / "shared/src/com/github/shariest/g70/vehicle/Telemetry.java",
               ROOT / "shared/src/com/github/shariest/g70/i18n/UiText.java",
               ROOT / "apps/dashboard/src/com/github/shariest/g70/dashboard/HscanFrameDecoder.java"]
    sources += [hub / (name + ".java") for name in ("RadioTrial", "BluetoothSession", "RebootRequest", "QuickEntryPolicy")]
    tests = sorted((ROOT / "tests").glob("*Test.java"))
    sources += [ROOT / "shared/src/com/github/shariest/g70/diagnostics/ErrorLog.java", ROOT / "shared/src/com/github/shariest/g70/vehicle/DashboardPage.java"]
    sources += tests
    with tempfile.TemporaryDirectory(prefix="g70-public-tests-") as temporary:
        subprocess.run([str(javac), "-encoding", "UTF-8", "-d", temporary,
                        *map(str, sources)], check=True)
        for test in tests:
            cls = ("com.github.shariest.g70.dashboard." if test.stem == "HscanFrameDecoderTest" else "") + test.stem
            subprocess.run([str(java), "-ea", "-cp", temporary, cls], check=True)
    print("PASS: " + str(len(tests)) + " synthetic host test programs. Vehicle operation is outside this scope.")


if __name__ == "__main__":
    main()
