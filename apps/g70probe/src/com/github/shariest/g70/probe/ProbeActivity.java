package com.github.shariest.g70.probe;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/** Launches the read-only probe and shows a short status while it runs. */
public final class ProbeActivity extends Activity {
    private final Probe probe = new Probe();

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        String captureId = getIntent().getStringExtra("capture_id");
        if (captureId == null || !captureId.matches("capture-[0-9]{8}-[0-9]{6}-[A-Za-z0-9]{6}")) {
            captureId = null;
        }
        TextView view = new TextView(this);
        view.setPadding(48, 96, 48, 48);
        view.setTextSize(18);
        view.setText("G70 Probe\n\n"
            + "읽기 전용. 화면 켜둔 채로 두세요.\n"
            + "정차 상태에서 수집이 끝나면 노트북에서 Enter를 누르세요.");
        setContentView(view);
        probe.start(view, captureId);
    }

    @Override protected void onDestroy() { probe.stop(); super.onDestroy(); }
}
