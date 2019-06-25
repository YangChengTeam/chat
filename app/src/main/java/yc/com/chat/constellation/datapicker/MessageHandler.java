package yc.com.chat.constellation.datapicker;

import android.os.Handler;
import android.os.Message;

final class MessageHandler extends Handler {
    /* renamed from: a */
    final LoopView loopView;

    MessageHandler(LoopView loopview) {
        this.loopView = loopview;
    }

    public final void handleMessage(Message paramMessage) {
        if (paramMessage.what == 1000) {
            this.loopView.invalidate();
        }
        if (paramMessage.what == 2000) {
            LoopView.m34b(this.loopView);
        } else if (paramMessage.what == 3000) {
            this.loopView.mo9373c();
        }
        super.handleMessage(paramMessage);
    }
}