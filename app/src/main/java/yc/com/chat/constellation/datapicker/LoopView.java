package yc.com.chat.constellation.datapicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;
import java.util.Timer;

public class LoopView extends View {
    List arrayList;
    int colorBlack;
    int colorGray;
    int colorGrayLight;
    Context context;
    /* renamed from: g */
    int f67g;
    private GestureDetector gestureDetector;
    /* renamed from: h */
    int f68h;
    Handler handler;
    boolean isLoop;
    /* renamed from: l */
    float f69l;
    LoopListener loopListener;
    int mCurrentItem;
    private int mSelectItem;
    Timer mTimer;
    /* renamed from: n */
    int f70n;
    /* renamed from: o */
    int f71o;
    Paint paintA;
    Paint paintB;
    Paint paintC;
    int positon;
    /* renamed from: r */
    int f72r;
    /* renamed from: s */
    int f73s;
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;
    /* renamed from: t */
    int f74t;
    int textSize;
    int totalScrollY;
    /* renamed from: u */
    int f75u;
    /* renamed from: v */
    int f76v;
    /* renamed from: w */
    int f77w;
    /* renamed from: x */
    float f78x;
    /* renamed from: y */
    float f79y;
    /* renamed from: z */
    float f80z;

    public LoopView(Context context) {
        super(context);
        initLoopView(context);
    }

    public LoopView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        initLoopView(context);
    }

    public LoopView(Context context, AttributeSet attributeset, int i1) {
        super(context, attributeset, i1);
        initLoopView(context);
    }

    /* renamed from: b */
    static void m34b(LoopView loopview) {
        loopview.m37f();
    }

    /* renamed from: d */
    private void m35d() {
        if (this.arrayList != null) {
            this.paintA = new Paint();
            this.paintA.setColor(this.colorGray);
            this.paintA.setAntiAlias(true);
            this.paintA.setTypeface(Typeface.MONOSPACE);
            this.paintA.setTextSize((float) this.textSize);
            this.paintB = new Paint();
            this.paintB.setColor(this.colorBlack);
            this.paintB.setAntiAlias(true);
            this.paintB.setTextScaleX(1.05f);
            this.paintB.setTypeface(Typeface.MONOSPACE);
            this.paintB.setTextSize((float) this.textSize);
            this.paintC = new Paint();
            this.paintC.setColor(this.colorGrayLight);
            this.paintC.setAntiAlias(true);
            this.paintC.setTypeface(Typeface.MONOSPACE);
            this.paintC.setTextSize((float) this.textSize);
            if (Build.VERSION.SDK_INT >= 11) {
                setLayerType(1, null);
            }
            this.gestureDetector = new GestureDetector(this.context, this.simpleOnGestureListener);
            this.gestureDetector.setIsLongpressEnabled(false);
            m36e();
            this.f74t = (int) ((((float) this.f68h) * this.f69l) * ((float) (this.f72r - 1)));
            this.f73s = (int) (((double) (this.f74t * 2)) / 3.141592653589793d);
            this.f75u = (int) (((double) this.f74t) / 3.141592653589793d);
            this.f76v = this.f67g + this.textSize;
            this.f70n = (int) ((((float) this.f73s) - (this.f69l * ((float) this.f68h))) / 2.0f);
            this.f71o = (int) ((((float) this.f73s) + (this.f69l * ((float) this.f68h))) / 2.0f);
            if (this.positon == -1) {
                if (this.isLoop) {
                    this.positon = (this.arrayList.size() + 1) / 2;
                } else {
                    this.positon = 0;
                }
            }
            this.mCurrentItem = this.positon;
        }
    }

    /* renamed from: e */
    private void m36e() {
        Rect rect = new Rect();
        for (int i = 0; i < this.arrayList.size(); i++) {
            this.paintB.getTextBounds("00000000", 0, "00000000".length(), rect);
            int width = (int) (((float) rect.width()) * 4.0f);
            if (width > this.f67g) {
                this.f67g = width;
            }
            this.paintB.getTextBounds("星期", 0, 2, rect);
            width = rect.height();
            if (width > this.f68h) {
                this.f68h = width;
            }
        }
    }

    /* renamed from: f */
    private void m37f() {
        int i = (int) (((float) this.totalScrollY) % (this.f69l * ((float) this.f68h)));
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new MTimer(this, i, timer), 0, 10);
    }

    static int getSelectItem(LoopView loopview) {
        return loopview.getCurrentItem();
    }

    private void initLoopView(Context context) {
        this.textSize = 0;
        this.colorGray = -5789785;
        this.colorBlack = -13421773;
        this.colorGrayLight = -2302756;
        this.f69l = 2.0f;
        this.isLoop = true;
        this.positon = -1;
        this.f72r = 9;
        this.f78x = 0.0f;
        this.f79y = 0.0f;
        this.f80z = 0.0f;
        this.totalScrollY = 0;
        this.simpleOnGestureListener = new LoopViewGestureListener(this);
        this.handler = new MessageHandler(this);
        this.context = context;
        setTextSize(16.0f);
    }

    /* renamed from: b */
    protected final void mo9371b(float f1) {
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new LoopTimerTask(this, f1, timer), 0, 20);
    }

    /* renamed from: b */
    protected final void mo9372b(int i1) {
        Timer timer = new Timer();
        this.mTimer = timer;
        timer.schedule(new MyTimerTask(this, i1, timer), 0, 20);
    }

    /* renamed from: c */
    protected final void mo9373c() {
        if (this.loopListener != null) {
            new Handler().postDelayed(new LoopRunnable(this), 200);
        }
    }

    public final int getCurrentItem() {
        return this.mCurrentItem <= 0 ? 0 : this.mCurrentItem;
    }

    public final String getCurrentItemValue() {
        return String.valueOf(this.arrayList.get(getCurrentItem())).trim();
    }

    protected void onDraw(Canvas canvas) {
        if (this.arrayList == null) {
            super.onDraw(canvas);
            return;
        }
        int i;
        String[] strArr = new String[this.f72r];
        this.f77w = (int) (((float) this.totalScrollY) / (this.f69l * ((float) this.f68h)));
        this.mCurrentItem = this.positon + (this.f77w % this.arrayList.size());
        if (this.isLoop) {
            if (this.mCurrentItem < 0) {
                this.mCurrentItem = this.arrayList.size() + this.mCurrentItem;
            }
            if (this.mCurrentItem > this.arrayList.size() - 1) {
                this.mCurrentItem -= this.arrayList.size();
            }
        } else {
            if (this.mCurrentItem < 0) {
                this.mCurrentItem = 0;
            }
            if (this.mCurrentItem > this.arrayList.size() - 1) {
                this.mCurrentItem = this.arrayList.size() - 1;
            }
        }
        int i2 = (int) (((float) this.totalScrollY) % (this.f69l * ((float) this.f68h)));
        for (i = 0; i < this.f72r; i++) {
            int i3 = this.mCurrentItem - (4 - i);
            if (this.isLoop) {
                int i4 = i3;
                if (i3 < 0) {
                    i4 = i3 + this.arrayList.size();
                }
                i3 = i4;
                if (i4 > this.arrayList.size() - 1) {
                    i3 = i4 - this.arrayList.size();
                }
                strArr[i] = (String) this.arrayList.get(i3);
            } else if (i3 < 0) {
                strArr[i] = "";
            } else if (i3 > this.arrayList.size() - 1) {
                strArr[i] = "";
            } else {
                strArr[i] = (String) this.arrayList.get(i3);
            }
        }
        i = (this.f76v - this.f67g) / 2;
        canvas.drawLine(0.0f, (float) this.f70n, (float) this.f76v, (float) this.f70n, this.paintC);
        canvas.drawLine(0.0f, (float) this.f71o, (float) this.f76v, (float) this.f71o, this.paintC);
        for (int i5 = 0; i5 < this.f72r; i5++) {
            canvas.save();
            double d = (((double) ((((float) (this.f68h * i5)) * this.f69l) - ((float) i2))) * 3.141592653589793d) / ((double) this.f74t);
            float f = (float) (90.0d - ((d / 3.141592653589793d) * 180.0d));
            if (f >= 90.0f || f <= -90.0f) {
                canvas.restore();
            } else {
                int cos = (int) ((((double) this.f75u) - (Math.cos(d) * ((double) this.f75u))) - ((Math.sin(d) * ((double) this.f68h)) / 2.0d));
                canvas.translate(0.0f, (float) cos);
                canvas.scale(1.0f, (float) Math.sin(d));
                String str = strArr[i5];
                int left = this.f70n + (getLeft() * 1);
                Rect rect = new Rect();
                this.paintB.getTextBounds(str, 0, str.length(), rect);
                int width = rect.width();
                int width2 = getWidth() - (left * 2);
                if (width > 0) {
                    left = (int) (((double) left) + (((double) (width2 - width)) * 0.5d));
                }
                if (cos <= this.f70n && this.f68h + cos >= this.f70n) {
                    canvas.save();
                    canvas.clipRect(0, 0, this.f76v, this.f70n - cos);
                    canvas.drawText(strArr[i5], (float) left, (float) this.f68h, this.paintA);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, this.f70n - cos, this.f76v, (int) (((float) this.f68h) * this.f69l));
                    canvas.drawText(strArr[i5], (float) left, (float) this.f68h, this.paintB);
                    canvas.restore();
                } else if (cos <= this.f71o && this.f68h + cos >= this.f71o) {
                    canvas.save();
                    canvas.clipRect(0, 0, this.f76v, this.f71o - cos);
                    canvas.drawText(strArr[i5], (float) left, (float) this.f68h, this.paintB);
                    canvas.restore();
                    canvas.save();
                    canvas.clipRect(0, this.f71o - cos, this.f76v, (int) (((float) this.f68h) * this.f69l));
                    canvas.drawText(strArr[i5], (float) left, (float) this.f68h, this.paintA);
                    canvas.restore();
                } else if (cos < this.f70n || this.f68h + cos > this.f71o) {
                    canvas.clipRect(0, 0, this.f76v, (int) (((float) this.f68h) * this.f69l));
                    canvas.drawText(strArr[i5], (float) left, (float) this.f68h, this.paintA);
                } else {
                    canvas.clipRect(0, 0, this.f76v, (int) (((float) this.f68h) * this.f69l));
                    canvas.drawText(strArr[i5], (float) left, (float) this.f68h, this.paintB);
                    this.mSelectItem = this.arrayList.indexOf(strArr[i5]);
                }
                canvas.restore();
            }
        }
        super.onDraw(canvas);
    }

    protected void onMeasure(int i1, int j1) {
        m35d();
        setMeasuredDimension(this.f76v, this.f73s);
    }

    public boolean onTouchEvent(MotionEvent motionevent) {
        switch (motionevent.getAction()) {
            case 0:
                this.f78x = motionevent.getRawY();
                break;
            case 2:
                this.f79y = motionevent.getRawY();
                this.f80z = this.f78x - this.f79y;
                this.f78x = this.f79y;
                this.totalScrollY = (int) (((float) this.totalScrollY) + this.f80z);
                if (!this.isLoop && this.totalScrollY <= ((int) (((float) (-this.positon)) * (this.f69l * ((float) this.f68h))))) {
                    this.totalScrollY = (int) (((float) (-this.positon)) * (this.f69l * ((float) this.f68h)));
                    break;
                }
            default:
                if (!this.gestureDetector.onTouchEvent(motionevent) && motionevent.getAction() == 1) {
                    m37f();
                    break;
                }
        }
        if (this.totalScrollY < ((int) (((float) ((this.arrayList.size() - 1) - this.positon)) * (this.f69l * ((float) this.f68h))))) {
            invalidate();
        } else {
            this.totalScrollY = (int) (((float) ((this.arrayList.size() - 1) - this.positon)) * (this.f69l * ((float) this.f68h)));
            invalidate();
        }
        if (!this.gestureDetector.onTouchEvent(motionevent) && motionevent.getAction() == 1) {
            m37f();
        }
        return true;
    }

    public final void setArrayList(List arraylist) {
        this.arrayList = arraylist;
        m35d();
        invalidate();
    }

    public final void setCurrentItem(int position) {
        this.positon = position;
        this.totalScrollY = 0;
        m37f();
        invalidate();
    }

    public final void setCyclic(boolean cyclic) {
        this.isLoop = cyclic;
    }

    public final void setListener(LoopListener LoopListener) {
        this.loopListener = LoopListener;
    }

    public final void setNotLoop() {
        this.isLoop = false;
    }

    public final void setTextSize(float size) {
        if (size > 0.0f) {
            this.textSize = (int) (this.context.getResources().getDisplayMetrics().density * size);
        }
    }
}