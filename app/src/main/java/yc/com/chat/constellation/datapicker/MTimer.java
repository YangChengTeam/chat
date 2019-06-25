package yc.com.chat.constellation.datapicker;

import java.util.Timer;
import java.util.TimerTask;

final class MTimer extends TimerTask {
    /* renamed from: a */
    int f81a = Integer.MAX_VALUE;
    /* renamed from: b */
    int f82b = 0;
    /* renamed from: c */
    final int f83c;
    final LoopView loopView;
    final Timer timer;

    MTimer(LoopView loopview, int i, Timer timer) {
        this.loopView = loopview;
        this.f83c = i;
        this.timer = timer;
    }

    public final void run() {
        if (this.f81a == Integer.MAX_VALUE) {
            if (this.f83c < 0) {
                if (((float) (-this.f83c)) > (this.loopView.f69l * ((float) this.loopView.f68h)) / 2.0f) {
                    this.f81a = (int) (((-this.loopView.f69l) * ((float) this.loopView.f68h)) - ((float) this.f83c));
                } else {
                    this.f81a = -this.f83c;
                }
            } else if (((float) this.f83c) > (this.loopView.f69l * ((float) this.loopView.f68h)) / 2.0f) {
                this.f81a = (int) ((this.loopView.f69l * ((float) this.loopView.f68h)) - ((float) this.f83c));
            } else {
                this.f81a = -this.f83c;
            }
        }
        this.f82b = (int) (((float) this.f81a) * 0.1f);
        if (this.f82b == 0) {
            if (this.f81a < 0) {
                this.f82b = -1;
            } else {
                this.f82b = 1;
            }
        }
        if (Math.abs(this.f81a) <= 0) {
            this.timer.cancel();
            this.loopView.handler.sendEmptyMessage(3000);
            return;
        }
        LoopView loopView = this.loopView;
        loopView.totalScrollY += this.f82b;
        this.loopView.handler.sendEmptyMessage(1000);
        this.f81a -= this.f82b;
    }
}