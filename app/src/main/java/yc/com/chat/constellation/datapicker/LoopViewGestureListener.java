package yc.com.chat.constellation.datapicker;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

final class LoopViewGestureListener extends SimpleOnGestureListener {
    final LoopView loopView;

    LoopViewGestureListener(LoopView loopview) {
        this.loopView = loopview;
    }

    public final boolean onDown(MotionEvent motionevent) {
        if (this.loopView.mTimer != null) {
            this.loopView.mTimer.cancel();
        }
        return true;
    }

    public final boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1) {
        this.loopView.mo9371b(f1);
        return true;
    }
}