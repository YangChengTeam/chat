package yc.com.chat.constellation.datapicker;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class WheelScroller {
    public static final int MIN_DELTA_FOR_SCROLLING = 1;
    private static final int SCROLLING_DURATION = 400;
    private final int MESSAGE_JUSTIFY = 1;
    private final int MESSAGE_SCROLL = 0;
    private Handler animationHandler = new C04552();
    private Context context;
    private GestureDetector gestureDetector;
    private SimpleOnGestureListener gestureListener = new C04541();
    private boolean isScrollingPerformed;
    private int lastScrollY;
    private float lastTouchedY;
    private ScrollingListener listener;
    private Scroller scroller;

    /* renamed from: com.codbking.widget.view.WheelScroller$1 */
    class C04541 extends SimpleOnGestureListener {
        C04541() {
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            WheelScroller.this.lastScrollY = 0;
            WheelScroller.this.scroller.fling(0, WheelScroller.this.lastScrollY, 0, (int) (-velocityY), 0, 0, -2147483647, Integer.MAX_VALUE);
            WheelScroller.this.setNextMessage(0);
            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }
    }

    /* renamed from: com.codbking.widget.view.WheelScroller$2 */
    class C04552 extends Handler {
        C04552() {
        }

        public void handleMessage(Message msg) {
            WheelScroller.this.scroller.computeScrollOffset();
            int currY = WheelScroller.this.scroller.getCurrY();
            int access$000 = WheelScroller.this.lastScrollY - currY;
            WheelScroller.this.lastScrollY = currY;
            if (access$000 != 0) {
                WheelScroller.this.listener.onScroll(access$000);
            }
            if (Math.abs(currY - WheelScroller.this.scroller.getFinalY()) < 1) {
                currY = WheelScroller.this.scroller.getFinalY();
                WheelScroller.this.scroller.forceFinished(true);
            }
            if (!WheelScroller.this.scroller.isFinished()) {
                WheelScroller.this.animationHandler.sendEmptyMessage(msg.what);
            } else if (msg.what == 0) {
                WheelScroller.this.justify();
            } else {
                WheelScroller.this.finishScrolling();
            }
        }
    }

    public interface ScrollingListener {
        void onFinished();

        void onJustify();

        void onScroll(int i);

        void onStarted();
    }

    public WheelScroller(Context context, ScrollingListener listener) {
        this.gestureDetector = new GestureDetector(context, this.gestureListener);
        this.gestureDetector.setIsLongpressEnabled(false);
        this.scroller = new Scroller(context);
        this.listener = listener;
        this.context = context;
    }

    private void clearMessages() {
        this.animationHandler.removeMessages(0);
        this.animationHandler.removeMessages(1);
    }

    private void justify() {
        this.listener.onJustify();
        setNextMessage(1);
    }

    private void setNextMessage(int message) {
        clearMessages();
        this.animationHandler.sendEmptyMessage(message);
    }

    private void startScrolling() {
        if (!this.isScrollingPerformed) {
            this.isScrollingPerformed = true;
            this.listener.onStarted();
        }
    }

    void finishScrolling() {
        if (this.isScrollingPerformed) {
            this.listener.onFinished();
            this.isScrollingPerformed = false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.lastTouchedY = event.getY();
                this.scroller.forceFinished(true);
                clearMessages();
                break;
            case 2:
                int y = (int) (event.getY() - this.lastTouchedY);
                if (y != 0) {
                    startScrolling();
                    this.listener.onScroll(y);
                    this.lastTouchedY = event.getY();
                    break;
                }
                break;
        }
        if (!this.gestureDetector.onTouchEvent(event) && event.getAction() == 1) {
            justify();
        }
        return true;
    }

    public void scroll(int distance, int time) {
        this.scroller.forceFinished(true);
        this.lastScrollY = 0;
        this.scroller.startScroll(0, 0, 0, distance, time != 0 ? time : SCROLLING_DURATION);
        setNextMessage(0);
        startScrolling();
    }

    public void setInterpolator(Interpolator interpolator) {
        this.scroller.forceFinished(true);
        this.scroller = new Scroller(this.context, interpolator);
    }

    public void stopScrolling() {
        this.scroller.forceFinished(true);
    }
}