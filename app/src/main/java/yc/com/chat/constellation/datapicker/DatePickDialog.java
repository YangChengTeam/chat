package yc.com.chat.constellation.datapicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.view.WindowManager.LayoutParams;

import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import yc.com.chat.R;

public class DatePickDialog extends Dialog implements OnChangeLisener {
    private TextView cancel;
    private String format;
    private DatePicker mDatePicker;
    private TextView messgeTv;
    private OnChangeLisener onChangeLisener;
    private OnSureLisener onSureLisener;
    private Date startDate = new Date();
    private TextView sure;
    private String title;
    private TextView titleTv;
    private DateType type = DateType.TYPE_ALL;
    private FrameLayout wheelLayout;
    private int yearLimt = 5;



    public DatePickDialog(Context context) {
        super(context, R.style.dialog_style);
    }

    private DatePicker getDatePicker() {
        DatePicker datePicker = new DatePicker(getContext(), this.type);
        datePicker.setStartDate(this.startDate);
        datePicker.setYearLimt(this.yearLimt);
        datePicker.setOnChangeLisener(this);
        datePicker.init();
        return datePicker;
    }

    private void initParas() {
        LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 80;
        attributes.width = DateUtils.getScreenWidth(getContext());
        getWindow().setAttributes(attributes);
    }

    private void initView() {
        this.sure = findViewById(R.id.sure);
        this.cancel = findViewById(R.id.cancel);
        this.wheelLayout = findViewById(R.id.wheelLayout);
        this.titleTv = findViewById(R.id.title);
        this.messgeTv = findViewById(R.id.message);
        this.mDatePicker = getDatePicker();
        this.wheelLayout.addView(this.mDatePicker);
        this.titleTv.setText(this.title);
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        this.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickDialog.this.dismiss();
                if (DatePickDialog.this.onSureLisener != null) {
                    DatePickDialog.this.onSureLisener.onSure(DatePickDialog.this.mDatePicker.getSelectDate());
                }
            }
        });
    }

    public void onChanged(Date date) {
        if (this.onChangeLisener != null) {
            this.onChangeLisener.onChanged(date);
        }
        if (!TextUtils.isEmpty(this.format)) {
            CharSequence charSequence = "";
            try {
                charSequence = new SimpleDateFormat(this.format).format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.messgeTv.setText(charSequence);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cbk_dialog_pick_time);
        initView();
        initParas();
    }

    public void setMessageFormat(String format) {
        this.format = format;
    }

    public void setOnChangeLisener(OnChangeLisener onChangeLisener) {
        this.onChangeLisener = onChangeLisener;
    }

    public void setOnSureLisener(OnSureLisener onSureLisener) {
        this.onSureLisener = onSureLisener;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(DateType type) {
        this.type = type;
    }

    public void setYearLimt(int yearLimt) {
        this.yearLimt = yearLimt;
    }
}