package yc.com.chat.constellation.datapicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import yc.com.chat.R;

abstract class BaseWheelPick extends LinearLayout implements OnWheelChangedListener, OnWheelScrollListener {
    protected Context ctx;
    private GenWheelText genView;
    protected int selectColor = -12303292;
    protected int split = -2236963;
    protected int splitHeight = 1;
    protected int textColor = -2236963;

    public BaseWheelPick(Context context) {
        super(context);
        init(context);
    }

    public BaseWheelPick(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DatePicker);
        this.textColor = obtainStyledAttributes.getColor(R.styleable.DatePicker_picker_text_color, -2236963);
        this.selectColor = obtainStyledAttributes.getColor(R.styleable.DatePicker_picker_select_textColor, -12303292);
        this.split = obtainStyledAttributes.getColor(R.styleable.DatePicker_picker_split, -2236963);
        this.splitHeight = (int) obtainStyledAttributes.getDimension(R.styleable.DatePicker_picker_split_height, 0.5f);
        obtainStyledAttributes.recycle();
        init(context);
    }

    private void init(Context context) {
        this.genView = new GenWheelText(this.textColor);
        this.ctx = context;
        LayoutInflater.from(context).inflate(getLayout(), this);
    }

    protected String[] convertData(WheelView wheelView, Integer[] data) {
        return new String[0];
    }

    protected abstract int getItemHeight();

    protected abstract int getLayout();

    public void onChanged(WheelView wheel, int oldValue, int newValue) {
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStrokeWidth((float) this.splitHeight);
        paint.setColor(this.split);
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        int itemHeight = getItemHeight();
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(0.0f, (float) ((i + 1) * itemHeight), (float) getWidth(), (float) ((i + 1) * itemHeight), paint);
        }
    }

    protected abstract void setData(Object[] objArr);

    protected void setWheelListener(WheelView wheelView, Object[] data, boolean isCyclic) {
        WheelGeneralAdapter wheelGeneralAdapter = new WheelGeneralAdapter(this.ctx, this.genView);
        if (data[0] instanceof Integer) {
            wheelGeneralAdapter.setData(convertData(wheelView, (Integer[]) data));
        } else {
            wheelGeneralAdapter.setData(data);
        }
        wheelView.setSelectTextColor(this.textColor, this.selectColor);
        wheelView.setCyclic(isCyclic);
        wheelView.setViewAdapter(wheelGeneralAdapter);
        wheelView.addChangingListener(this);
        wheelView.addScrollingListener(this);
    }
}