package yc.com.chat.constellation.datapicker;

import java.util.Timer;
import java.util.TimerTask;

final class MyTimerTask extends TimerTask {
    /* renamed from: a */
    float f85a = 2.1474839E9f;
    /* renamed from: b */
    float f86b = 0.0f;
    /* renamed from: c */
    final int f87c;
    final LoopView loopView;
    final Timer timer;

    MyTimerTask(LoopView loopview, int i, Timer timer) {
        this.loopView = loopview;
        this.f87c = i;
        this.timer = timer;
    }

    public final void run() {
        if (this.f85a == 2.1474839E9f) {
            this.f85a = (((float) (this.f87c - LoopView.getSelectItem(this.loopView))) * this.loopView.f69l) * ((float) this.loopView.f68h);
            if (this.f87c > LoopView.getSelectItem(this.loopView)) {
                this.f86b = -1000.0f;
            } else {
                this.f86b = 1000.0f;
            }
        }
        if (Math.abs(this.f85a) < 1.0f) {
            this.timer.cancel();
            this.loopView.handler.sendEmptyMessage(2000);
            return;
        }
        int i = (int) ((this.f86b * 10.0f) / 1000.0f);
        int i2 = i;
        if (Math.abs(this.f85a) < ((float) Math.abs(i))) {
            i2 = (int) (-this.f85a);
        }
        LoopView loopView = this.loopView;
        loopView.totalScrollY -= i2;
        this.f85a = ((float) i2) + this.f85a;
        this.loopView.handler.sendEmptyMessage(1000);
    }
}