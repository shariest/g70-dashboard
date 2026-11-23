package com.github.shariest.g70.probe;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * Read-only discovery probe for a G70 standard Gen5 head unit.
 *
 * Three jobs, no interpretation:
 *   1. identity()  - Build.* so we confirm the exact platform/build.
 *   2. managers()  - which com.hkmc.* managers resolve, and their method surface.
 *   3. hscan()     - connect the abstract "hscan_mux" socket (NO product guard,
 *                    NO frame assumptions) and spool the raw bytes to a file.
 *
 * Frame decoding is deliberately NOT done here: the G70 framing may differ from
 * the stock Gen5 capture, so we save raw bytes and parse them off-device.
 */
final class Probe {
    static final String TAG = "G70PROBE";
    private static final int CAP = 256 * 1024; // stop raw capture after 256 KB

    private volatile boolean running;
    private Thread thread;
    private TextView status;
    private final Handler ui = new Handler(Looper.getMainLooper());

    void start(TextView status, final String captureId) {
        this.status = status;
        if (running) return;
        running = true;
        thread = new Thread(new Runnable() { public void run() {
            try {
                if (captureId != null) Log.i(TAG, "== " + captureId + " ==");
                identity();
                managers();
                hscan(captureId == null ? "g70_hscan.bin" : "g70-" + captureId + ".bin");
            }
            catch (Throwable t) { Log.e(TAG, "probe crashed", t); }
        }}, "g70-probe");
        thread.setDaemon(true);
        thread.start();
    }

    void stop() { running = false; if (thread != null) thread.interrupt(); }

    private void setStatus(final String line) {
        ui.post(new Runnable() { public void run() { if (status != null) status.append("\n" + line); } });
    }

    private void identity() {
        Log.i(TAG, "== IDENTITY ==");
        p("PRODUCT", Build.PRODUCT);
        p("MODEL", Build.MODEL);
        p("DEVICE", Build.DEVICE);
        p("HARDWARE", Build.HARDWARE);
        p("RELEASE", Build.VERSION.RELEASE);
        p("SDK_INT", String.valueOf(Build.VERSION.SDK_INT));
        p("INCREMENTAL", Build.VERSION.INCREMENTAL);
        p("FINGERPRINT", Build.FINGERPRINT);
    }
    private static void p(String key, String value) { Log.i(TAG, key + " = " + value); }

    private void managers() {
        Log.i(TAG, "== OEM MANAGERS (reflection) ==");
        String[] names = {
            "com.hkmc.VehicleInfoManager",
            "com.hkmc.micom.NModeManager",
            "com.hkmc.micom.VehicleInfoManager",
            "com.hkmc.bcm.BCMManager",
            "com.hkmc.pgs.PgsManager",
        };
        for (String name : names) {
            try {
                Class<?> type = Class.forName(name);
                StringBuilder methods = new StringBuilder();
                for (Method method : type.getMethods()) {
                    if (method.getDeclaringClass() == Object.class) continue;
                    methods.append("\n    ").append(method.getReturnType().getSimpleName())
                           .append(' ').append(method.getName()).append('(');
                    Class<?>[] params = method.getParameterTypes();
                    for (int i = 0; i < params.length; i++) {
                        if (i > 0) methods.append(',');
                        methods.append(params[i].getSimpleName());
                    }
                    methods.append(')');
                }
                Log.i(TAG, "PRESENT " + name + methods);
            } catch (Throwable missing) {
                Log.i(TAG, "absent  " + name + "  (" + missing.getClass().getSimpleName() + ")");
            }
        }
    }

    private void hscan(String filename) {
        Log.i(TAG, "== HSCAN_MUX ==");
        File target = new File(Environment.getExternalStorageDirectory(), filename);
        byte[] buffer = new byte[4096];
        long total = 0;
        boolean peeked = false;
        while (running) {
            LocalSocket socket = new LocalSocket();
            FileOutputStream output = null;
            try {
                socket.connect(new LocalSocketAddress("hscan_mux", LocalSocketAddress.Namespace.ABSTRACT));
                socket.setSoTimeout(500); // API17: connect before setting options
                InputStream input = socket.getInputStream();
                Log.i(TAG, "connected hscan_mux; writing " + target.getAbsolutePath());
                setStatus("hscan 연결됨");
                output = new FileOutputStream(target, total > 0);
                while (running) {
                    int count;
                    try { count = input.read(buffer); }
                    catch (java.net.SocketTimeoutException idle) { continue; }
                    if (count < 0) break;
                    if (count == 0) continue;
                    if (total < CAP) {
                        int write = (int) Math.min(count, CAP - total);
                        output.write(buffer, 0, write);
                        output.flush();
                        total += write;
                        if (!peeked) { peeked = true; Log.i(TAG, "first bytes: " + hex(buffer, Math.min(count, 64))); }
                        if (total >= CAP) {
                            Log.i(TAG, "raw capture full (" + CAP + " bytes) -> adb pull " + target.getAbsolutePath());
                            setStatus("캡처 완료 " + CAP + "B — pull 하세요");
                            return;
                        }
                    }
                }
            } catch (Throwable unavailable) {
                Log.w(TAG, "hscan_mux unavailable: " + unavailable);
                setStatus("hscan 미연결: " + unavailable);
            } finally {
                if (output != null) try { output.close(); } catch (Throwable ignored) { }
                try { socket.close(); } catch (Throwable ignored) { }
            }
            if (running) try { Thread.sleep(2000); } catch (InterruptedException stopped) { break; }
        }
    }

    private static String hex(byte[] bytes, int count) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < count; i++) out.append(String.format("%02x ", bytes[i] & 255));
        return out.toString().trim();
    }
}
