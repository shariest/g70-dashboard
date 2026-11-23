# G70 installation guide

[한국어](INSTALL.md) · [Compatibility](STATUS.md)

## Signal discovery with G70 Probe

Enter the project directory after cloning, or run `git pull` in your existing checkout. With the G70 parked, connect one head unit with USB debugging enabled. [USB debugging setup](ADB_EN.md)

- From **Windows PowerShell or Command Prompt**, run `.\run-probe.cmd`. It uses Bash from Git for Windows.
- From **macOS, Linux or Git Bash**, run `./run-probe.sh`.

The launcher uses the existing ADB installation or downloads the tools from Google on first use. It installs and opens the bundled `apps/g70probe/G70-Probe-debug.apk`. Check the probe screen, then press Enter on the laptop to collect the results. The terminal prints the capture directory. [Probe and output files](../apps/g70probe/README.md)

Android treats the new package ID as a separate app. If the earlier probe is still installed, the launcher opens `com.github.shariest.g70.probe/.ProbeActivity` explicitly.

## G70 Dashboard and G70 My Apps

Both apps build from source as unsigned APKs. A signed dashboard/launcher release and a verified G70 installation profile are pending. Archived APKs have different package IDs and cannot update or restore these apps. [Build guide](BUILD.md) · [Release history](RELEASE.md)

For future vehicle verification, record each result separately. Check installation, opening the dashboard from My Apps, reopening from HOME with the cable disconnected, and restoring the shortcut after a full restart. [Launcher](MY_APPS.md) · [Quick entry](QUICK_ENTRY.md)
