package yc.com.chat.constellation.datapicker;

import java.util.Timer;
import java.util.TimerTask;

final class LoopTimerTask extends TimerTask {
    /* renamed from: a */
    float f65a = 2.1474839E9f;
    /* renamed from: b */
    final float f66b;
    final LoopView loopView;
    final Timer timer;

    LoopTimerTask(LoopView loopview, float f, Timer timer) {
        this.loopView = loopview;
        this.f66b = f;
        this.timer = timer;
    }

    public final void run() {
        if (this.f65a == 2.1474839E9f) {
            if (Math.abs(this.f66b) <= 2000.0f) {
                this.f65a = this.f66b;
            } else if (this.f66b > 0.0f) {
                this.f65a = 2000.0f;
            } else {
                this.f65a = -2000.0f;
            }
        }
        if (Math.abs(this.f65a) < 0.0f || Math.abs(this.f65a) > 20.0f) {
            int i = (int) ((this.f65a * 10.0f) / 1000.0f);
            LoopView loopView = this.loopView;
            loopView.totalScrollY -= i;
            if (!this.loopView.isLoop) {
                if (this.loopView.totalScrollY <= ((int) (((float) (-this.loopView.positon)) * (this.loopView.f69l * ((float) this.loopView.f68h))))) {
                    this.f65a = 40.0f;
                    this.loopView.totalScrollY = (int) (((float) (-this.loopView.positon)) * (this.loopView.f69l * ((float) this.loopView.f68h)));
                } else if (this.loopView.totalScrollY >= ((int) (((float) ((this.loopView.arrayList.size() - 1) - this.loopView.positon)) * (this.loopView.f69l * ((float) this.loopView.f68h))))) {
                    this.loopView.totalScrollY = (int) (((float) ((this.loopView.arrayList.size() - 1) - this.loopView.positon)) * (this.loopView.f69l * ((float) this.loopView.f68h)));
                    this.f65a = -40.0f;
                }
            }
            if (this.f65a < 0.0f) {
                this.f65a += 20.0f;
            } else {
                this.f65a -= 20.0f;
            }
            this.loopView.handler.sendEmptyMessage(1000);
            return;
        }
        this.timer.cancel();
        this.loopView.handler.sendEmptyMessage(2000);
    }
}