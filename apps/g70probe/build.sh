#!/bin/bash
# Dev-side: (re)build the debug-signed G70 probe APK.
# Needs Android SDK build-tools + any platform android.jar. Auto-detects the SDK.
set -euo pipefail
HERE="$(cd "$(dirname "$0")" && pwd)"
SDK="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}}"
BT="$(ls -d "$SDK"/build-tools/*/ | sort -V | tail -1)"; BT="${BT%/}"
AJAR="$(ls "$SDK"/platforms/*/android.jar | sort -V | tail -1)"
OUT="$HERE/build"; rm -rf "$OUT"; mkdir -p "$OUT/classes"

# Stable debug key (reused across rebuilds so `adb install -r` keeps working).
KS="$HOME/.android/debug.keystore"
if [ ! -f "$KS" ]; then mkdir -p "$HOME/.android"
  keytool -genkeypair -keystore "$KS" -storepass android -keypass android \
    -alias androiddebugkey -dname "CN=Android Debug,O=Android,C=US" \
    -keyalg RSA -keysize 2048 -validity 10000 >/dev/null 2>&1; fi

javac --release 8 -Xlint:-options -cp "$AJAR" -d "$OUT/classes" $(find "$HERE/src" -name '*.java')
"$BT/d8" --min-api 17 --lib "$AJAR" --output "$OUT" $(find "$OUT/classes" -name '*.class')
"$BT/aapt2" link -I "$AJAR" --manifest "$HERE/AndroidManifest.xml" \
    --min-sdk-version 17 --target-sdk-version 17 -o "$OUT/base.apk"
(cd "$OUT" && zip -j base.apk classes.dex >/dev/null)
"$BT/zipalign" -f 4 "$OUT/base.apk" "$OUT/aligned.apk"
# V1 (JAR) signature is required by the API17 head unit. v4 off (no stray .idsig).
"$BT/apksigner" sign --ks "$KS" --ks-pass pass:android --key-pass pass:android \
    --min-sdk-version 17 --v1-signing-enabled true --v4-signing-enabled false \
    --out "$HERE/G70-Probe-debug.apk" "$OUT/aligned.apk"
rm -rf "$OUT"
"$BT/apksigner" verify -v "$HERE/G70-Probe-debug.apk" 2>/dev/null | grep -i 'v1 scheme'
echo "built: $HERE/G70-Probe-debug.apk"
