package com.github.shariest.g70.probe;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic hscan_mux frame scanner for G70 discovery.
 *
 * Reuses the EXACT container format the stock decoder validated
 * (HscanFrameDecoder): 264-byte frame, fixed 8-byte header, domain byte at [8],
 * little-endian entry count at [9..10], then {function,size,size-bytes} entries,
 * trailing zeros, whole-frame XOR == 0.
 *
 * The ONE difference vs. the shipping decoder: it does NOT whitelist the
 * stock function codes (7/0x23/0x53/0x5a/0x6e/0x53). It returns EVERY entry,
 * whatever the function code or domain byte, so we learn what the G70 actually
 * emits instead of assuming the stock signal set. No interpretation of the
 * payload bytes — raw only. Discovery, not decoding.
 */
public final class G70HscanScan {
    public static final int FRAME_LEN = 264;
    private static final int[] HEADER = {255, 11, 142, 1, 139, 199, 1, 0};

    public static final class Entry {
        public final int function, size;
        public final byte[] value;
        Entry(int function, int size, byte[] value) { this.function = function; this.size = size; this.value = value; }
        @Override public String toString() { return String.format("fn=0x%02x size=%d bytes=%s", function, size, hex(value)); }
    }

    public static final class Frame {
        public final int domain;              // byte[8] — 0xc7 on stock Gen5; unknown on G70
        public final List<Entry> entries;
        Frame(int domain, List<Entry> entries) { this.domain = domain; this.entries = entries; }
    }

    /** Returns the parsed frame, or null if the 264 bytes are not a valid container. */
    public static Frame scan(byte[] frame) {
        if (frame == null || frame.length != FRAME_LEN) return null;
        for (int i = 0; i < HEADER.length; i++) if ((frame[i] & 255) != HEADER[i]) return null;
        int checksum = 0;
        for (byte b : frame) checksum ^= b & 255;
        if (checksum != 0) return null;

        int domain = frame[8] & 255;
        int count = (frame[9] & 255) | ((frame[10] & 255) << 8);
        if (count > 84) return null;

        int offset = 11;
        // Validate the whole batch before trusting any entry (same discipline as the OEM decoder).
        for (int i = 0; i < count; i++) {
            if (offset + 2 > 263) return null;
            int size = frame[offset + 1] & 255;
            if (size < 1 || size > 8 || offset + 2 + size > 263) return null;
            offset += 2 + size;
        }
        for (int i = offset; i < 263; i++) if (frame[i] != 0) return null;

        List<Entry> entries = new ArrayList<Entry>(count);
        offset = 11;
        for (int i = 0; i < count; i++) {
            int function = frame[offset++] & 255, size = frame[offset++] & 255;
            byte[] value = new byte[size];
            System.arraycopy(frame, offset, value, 0, size);
            entries.add(new Entry(function, size, value));   // every entry, no whitelist
            offset += size;
        }
        return new Frame(domain, entries);
    }

    public static String hex(byte[] b) {
        StringBuilder s = new StringBuilder();
        for (byte x : b) s.append(String.format("%02x ", x & 255));
        return s.toString().trim();
    }

    // ---- build a synthetic stock-format frame, for the self-test ----
    static byte[] build(int domain, int[][] entries) {
        byte[] f = new byte[FRAME_LEN];
        for (int i = 0; i < HEADER.length; i++) f[i] = (byte) HEADER[i];
        f[8] = (byte) domain;
        f[9] = (byte) (entries.length & 255);
        f[10] = (byte) ((entries.length >> 8) & 255);
        int o = 11;
        for (int[] e : entries) {                 // e = {function, size, firstByte...}
            f[o++] = (byte) e[0];
            f[o++] = (byte) e[1];
            for (int k = 0; k < e[1]; k++) f[o++] = (byte) (e.length > 2 + k ? e[2 + k] : k + 1);
        }
        int x = 0;
        for (int i = 0; i < 263; i++) x ^= f[i] & 255;
        f[263] = (byte) x;                        // make whole-frame XOR == 0
        return f;
    }

    public static void main(String[] args) {
        // Known stock functions PLUS a non-stock code 0x99 → proves we don't whitelist.
        int[][] spec = {
            {0x07, 8}, {0x53, 5}, {0x5a, 8}, {0x99, 3, 0xaa, 0xbb, 0xcc},
        };
        Frame fr = scan(build(0xc7, spec));
        assert fr != null : "valid container must parse";
        assert fr.domain == 0xc7 : "domain";
        assert fr.entries.size() == spec.length : "entry count";
        assert fr.entries.get(3).function == 0x99 && fr.entries.get(3).size == 3 : "non-stock fn recovered";
        assert G70HscanScan.hex(fr.entries.get(3).value).equals("aa bb cc") : "payload bytes raw";
        // Corrupt the checksum → must reject.
        byte[] bad = build(0xc7, spec); bad[263] ^= 0x01;
        assert scan(bad) == null : "bad checksum rejected";
        // Wrong length → reject.
        assert scan(new byte[10]) == null : "short frame rejected";
        for (Entry e : fr.entries) System.out.println(e);
        System.out.println("G70HscanScan SELF-TEST OK");
    }
}
