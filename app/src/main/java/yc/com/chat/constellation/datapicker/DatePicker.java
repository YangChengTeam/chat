package yc.com.chat.constellation.datapicker;

import android.content.Context;
import android.widget.TextView;


import java.util.Date;

import yc.com.chat.R;

class DatePicker extends BaseWheelPick {
    private static final String TAG = "WheelPicker";
    private DatePickerHelper datePicker;
    private Integer[] dayArr;
    private WheelView dayView = (WheelView) findViewById(R.id.day);
    private Integer[] hourArr;
    private WheelView hourView;
    private Integer[] minutArr;
    private WheelView minuteView;
    private WheelView monthView;
    private Integer[] mothArr;
    private OnChangeLisener onChangeLisener;
    private int selectDay;
    private Date startDate = new Date();
    public DateType type = DateType.TYPE_ALL;
    private TextView weekView;
    private Integer[] yearArr;
    private int yearLimt = 5;
    private WheelView yearView;

    public DatePicker(Context context, DateType type) {
        super(context);
        if (this.type != null) {
            this.type = type;
        }
    }

    private void setChangeDaySelect(int year, int moth) {
        this.dayArr = this.datePicker.genDay(year, moth);
        ((WheelGeneralAdapter) this.dayView.getViewAdapter()).setData(convertData(this.dayView, this.dayArr));
        int findIndextByValue = this.datePicker.findIndextByValue(this.selectDay, this.dayArr);
        if (findIndextByValue == -1) {
            this.dayView.setCurrentItem(0);
        } else {
            this.dayView.setCurrentItem(findIndextByValue);
        }
    }

    protected String[] convertData(WheelView wheelView, Integer[] data) {
        return wheelView == this.yearView ? this.datePicker.getDisplayValue(data, "年") : wheelView == this.monthView ? this.datePicker.getDisplayValue(data, "月") : wheelView == this.dayView ? this.datePicker.getDisplayValue(data, "日") : wheelView == this.hourView ? this.datePicker.getDisplayValue(data, "") : wheelView == this.minuteView ? this.datePicker.getDisplayValue(data, "") : new String[0];
    }

    protected int getItemHeight() {
        return this.dayView.getItemHeight();
    }

    protected int getLayout() {
        return R.layout.cbk_wheel_picker;
    }

    public Date getSelectDate() {
        return DateUtils.getDate(this.yearArr[this.yearView.getCurrentItem()].intValue(), this.mothArr[this.monthView.getCurrentItem()].intValue(), this.dayArr[this.dayView.getCurrentItem()].intValue(), this.hourArr[this.hourView.getCurrentItem()].intValue(), this.minutArr[this.minuteView.getCurrentItem()].intValue());
    }

    public void init() {
        this.minuteView = findViewById(R.id.minute);
        this.hourView = findViewById(R.id.hour);
        this.weekView = findViewById(R.id.week);
        this.monthView = findViewById(R.id.month);
        this.yearView = findViewById(R.id.year);
        switch (this.type) {
            case TYPE_ALL:
                this.minuteView.setVisibility(VISIBLE);
                this.hourView.setVisibility(VISIBLE);
                this.weekView.setVisibility(VISIBLE);
                this.dayView.setVisibility(VISIBLE);
                this.monthView.setVisibility(VISIBLE);
                this.yearView.setVisibility(VISIBLE);
                break;
            case TYPE_YMDHM:
                this.minuteView.setVisibility(VISIBLE);
                this.hourView.setVisibility(VISIBLE);
                this.weekView.setVisibility(GONE);
                this.dayView.setVisibility(VISIBLE);
                this.monthView.setVisibility(VISIBLE);
                this.yearView.setVisibility(VISIBLE);
                break;
            case TYPE_YMDH:
                this.minuteView.setVisibility(GONE);
                this.hourView.setVisibility(VISIBLE);
                this.weekView.setVisibility(GONE);
                this.dayView.setVisibility(VISIBLE);
                this.monthView.setVisibility(VISIBLE);
                this.yearView.setVisibility(VISIBLE);
                break;
            case TYPE_YMD:
                this.minuteView.setVisibility(GONE);
                this.hourView.setVisibility(GONE);
                this.weekView.setVisibility(GONE);
                this.dayView.setVisibility(VISIBLE);
                this.monthView.setVisibility(VISIBLE);
                this.yearView.setVisibility(VISIBLE);
                break;
            case TYPE_HM:
                this.minuteView.setVisibility(VISIBLE);
                this.hourView.setVisibility(VISIBLE);
                this.weekView.setVisibility(GONE);
                this.dayView.setVisibility(GONE);
                this.monthView.setVisibility(GONE);
                this.yearView.setVisibility(GONE);
                break;
        }
        this.datePicker = new DatePickerHelper();
        this.datePicker.setStartDate(this.startDate, this.yearLimt);
        this.dayArr = this.datePicker.genDay();
        this.yearArr = this.datePicker.genYear();
        this.mothArr = this.datePicker.genMonth();
        this.hourArr = this.datePicker.genHour();
        this.minutArr = this.datePicker.genMinut();
        this.weekView.setText(this.datePicker.getDisplayStartWeek());
        setWheelListener(this.yearView, this.yearArr, false);
        setWheelListener(this.monthView, this.mothArr, true);
        setWheelListener(this.dayView, this.dayArr, true);
        setWheelListener(this.hourView, this.hourArr, true);
        setWheelListener(this.minuteView, this.minutArr, true);
        this.yearView.setCurrentItem(this.datePicker.findIndextByValue(this.datePicker.getToady(DatePickerHelper.Type.YEAR), this.yearArr));
        this.monthView.setCurrentItem(this.datePicker.findIndextByValue(this.datePicker.getToady(DatePickerHelper.Type.MOTH), this.mothArr));
        this.dayView.setCurrentItem(this.datePicker.findIndextByValue(this.datePicker.getToady(DatePickerHelper.Type.DAY), this.dayArr));
        this.hourView.setCurrentItem(this.datePicker.findIndextByValue(this.datePicker.getToady(DatePickerHelper.Type.HOUR), this.hourArr));
        this.minuteView.setCurrentItem(this.datePicker.findIndextByValue(this.datePicker.getToady(DatePickerHelper.Type.MINUTE), this.minutArr));
    }

    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        int intValue = this.yearArr[this.yearView.getCurrentItem()].intValue();
        int intValue2 = this.mothArr[this.monthView.getCurrentItem()].intValue();
        int intValue3 = this.dayArr[this.dayView.getCurrentItem()].intValue();
        int intValue4 = this.hourArr[this.hourView.getCurrentItem()].intValue();
        int intValue5 = this.minutArr[this.minuteView.getCurrentItem()].intValue();
        if (wheel == this.yearView || wheel == this.monthView) {
            setChangeDaySelect(intValue, intValue2);
        } else {
            this.selectDay = intValue3;
        }
        if (wheel == this.yearView || wheel == this.monthView || wheel == this.dayView) {
            this.weekView.setText(this.datePicker.getDisplayWeek(intValue, intValue2, intValue3));
        }
        if (this.onChangeLisener != null) {
            this.onChangeLisener.onChanged(DateUtils.getDate(intValue, intValue2, intValue3, intValue4, intValue5));
        }
    }



    protected void setData(Object[] datas) {
    }

    public void setOnChangeLisener(OnChangeLisener onChangeLisener) {
        this.onChangeLisener = onChangeLisener;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setYearLimt(int yearLimt) {
        this.yearLimt = yearLimt;
    }

    @Override
    public void onScrollingFinished(yc.com.chat.constellation.datapicker.WheelView wheelView) {

    }

    @Override
    public void onScrollingStarted(yc.com.chat.constellation.datapicker.WheelView wheelView) {

    }
}