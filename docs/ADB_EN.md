# Enable USB debugging on the G70 head unit

[한국어](ADB.md) · [Probe installation](INSTALL_EN.md)

Park the vehicle before operating the head unit. If the laptop already lists the head unit as the single `device` in `adb devices -l`, continue to Probe installation.

## 1. Identify the head unit

On a Korean Standard Gen5 unit, open **홈 → 전체메뉴 → 설정 → 일반 → SW정보/업데이트** (Home → All menus → Settings → General → SW info/update). Record the model and full software version. The project is investigating the Korean 2019 G70 with the model string `IKBS.S5AMC.KR` and a version ending in `260114.STD_H`. [Genesis software version guide](https://update.genesis.com/KR/KO/cs/faq/2353)

## 2. Enable debugging using the available settings

| Screen on the head unit | What to do |
| --- | --- |
| Android **Settings → Developer options** is visible | Turn on **USB debugging** and confirm the prompt if shown. |
| Developer options is hidden and Android **About device → Build number** is visible | Tap **Build number** seven times. Return to Settings, open **Developer options** and turn on **USB debugging**. This applies to units that expose the standard Android settings. |
| Only the factory navigation menus are visible | The project's candidate service-menu path is **Engineering mode → Diagnosis → Factory Test**. Entry and USB debugging activation on the target G70 still need vehicle verification. If the normal settings omit USB debugging, give the head-unit manufacturer the model and full version recorded above and ask which USB diagnostic procedure it supports. |

Android's **Build number** is an item in Android settings. The Genesis **SW info/update** page identifies the navigation software. [Android developer options](https://developer.android.com/studio/debug/dev-options) · [Genesis head-unit support contacts](https://update.genesis.com/KR/KO/cs/faq/2353)

## 3. Check the connection on the laptop

Connect a data-capable USB cable and port. The [probe launcher](INSTALL_EN.md) prepares ADB and shows the device list. Keep only the intended Android head unit connected over USB. If ADB is already on PATH, you can also check manually. [Platform-Tools downloads](https://developer.android.com/tools/releases/platform-tools)

```sh
adb devices -l
```

| Result | Next step |
| --- | --- |
| The head unit is the only entry and reads `device` | Continue to Probe installation. |
| `unauthorized` | Accept the USB debugging prompt for this laptop if it appears on the head unit, then recheck. |
| `offline` | Reconnect the cable and check the listing again. |
| Empty list | Check whether the laptop detects the head unit as a USB device. If absent, check the cable and port. If present, check USB debugging on the head unit. On Windows, also check the ADB USB driver. |

To record the running Android build, substitute the serial from the listing. [Android USB connection guide](https://developer.android.com/tools/adb)

```sh
adb -s SERIAL shell getprop ro.product.name
adb -s SERIAL shell getprop ro.build.version.sdk
adb -s SERIAL shell getprop ro.build.version.incremental
```

After capture, check that the output is on the laptop. If you enabled USB debugging in Android Developer options for this session, you can turn it off using the same switch. [Install and collect probe data](INSTALL_EN.md)
