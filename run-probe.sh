#!/usr/bin/env bash
# Run the bundled APK on one connected head unit after clone or pull.
set -euo pipefail
root=$(cd "$(dirname "$0")" && pwd)

# Prefer the user's ADB. Cache a verified Google distribution for a fresh laptop.
prepare_adb() {
    if command -v adb >/dev/null; then return; fi
    local sdk_dir
    for sdk_dir in "${ANDROID_HOME:-}" "${ANDROID_SDK_ROOT:-}"; do
        if [[ -n "$sdk_dir" && -x "$sdk_dir/platform-tools/adb" ]]; then
            export PATH="$sdk_dir/platform-tools:$PATH"
            return
        fi
    done

    local platform digest
    case "$(uname -s)" in
        Darwin) platform=darwin; digest=106a5d31fad8c1c0c5a180d06f5779767d129d7d5edbe629005c11a85eec5b4b ;;
        Linux) platform=linux; digest=3afdea91441815ab41254193df0343d92c1b1c0d0237165c3a345c8af8891c31 ;;
        MINGW*|MSYS*) platform=win; digest=b024d4f319d6ad3004de1ba7b96a5c7c5f3512e8b14126308d598b4ab93dcead ;;
        *) echo "이 환경에서는 Android Platform-Tools의 adb를 PATH에 추가하세요." >&2; return 1 ;;
    esac
    local version=36.0.2
    local cache="$root/build/android-tools-$platform-$version"
    if [[ ! -x "$cache/platform-tools/adb" ]]; then
        mkdir -p "$cache"
        (
            temp=$(mktemp -d "$cache/download.XXXXXX")
            trap 'rm -rf "$temp"' EXIT
            cd "$temp"
            echo "Google에서 Android Platform-Tools $version 다운로드 중…" >&2
            curl --fail --location --silent --show-error --retry 2 \
                "https://dl.google.com/android/repository/platform-tools_r$version-$platform.zip" -o tools.zip
            if command -v sha256sum >/dev/null; then
                printf '%s  tools.zip\n' "$digest" | sha256sum -c -
            else
                printf '%s  tools.zip\n' "$digest" | shasum -a 256 -c -
            fi
            if command -v unzip >/dev/null; then unzip -q tools.zip; else tar -xf tools.zip; fi
            mv platform-tools "$cache/" || test -x "$cache/platform-tools/adb"
        )
    fi
    export PATH="$cache/platform-tools:$PATH"
}

prepare_adb
exec bash "$root/apps/g70probe/run_probe.sh"
