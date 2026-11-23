# G70 My Apps

[한국어](MY_APPS_ko.md) · [Installation status](INSTALL_EN.md)

G70 My Apps (`com.github.shariest.g70.hub`) is the launcher for the dashboard, other installed apps and accessible head-unit settings. The following controls describe the source build.

| Control | Behavior |
| --- | --- |
| G70 Dashboard | Opens the separate `com.github.shariest.g70.dashboard/.DashboardActivity`. |
| Quick entry | Saves Floating + notification, Notification only or Off. The default is Off. |
| Edit app list | Selects the apps shown in the launcher. The selection is stored locally. |
| Language | Selects the system language, Korean or English. |
| Android utilities and Engineering mode | Opens settings screens available to external apps on the head unit. |
| Bluetooth settings | Uses Android discovery and pairing. |
| USB debugging and Restart head unit | Depend on OEM support and permissions. Restart requires confirmation. |
| Connection checks | Shows network state and the experimental hotspot controls. Wireless CarPlay is unimplemented. |

Each app keeps its own language setting. On a G70 test installation, opening the dashboard from My Apps and reopening My Apps from HOME are separate screen checks. [Languages](LANGUAGES.md) · [Quick entry](QUICK_ENTRY.md)
