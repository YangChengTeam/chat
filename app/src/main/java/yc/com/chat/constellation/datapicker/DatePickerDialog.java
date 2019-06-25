package yc.com.chat.constellation.datapicker;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import yc.com.chat.R;

public class DatePickerDialog extends Dialog {
    private static int MAX_YEAR = 2049;
    private static int MIN_YEAR = 1901;
    private Params params;

    public static class Builder {
        private final Context context;
        private Integer maxDay;
        private Integer maxMonth;
        private Integer maxYear;
        private Integer minDay;
        private Integer minMonth;
        private Integer minYear;
        private final Params params = new Params();
        private Integer selectDay;
        private Integer selectMonth;
        private Integer selectYear;

        public Builder(Context context) {
            this.context = context;
        }

        /* renamed from: d */
        private static List<String> m33d(int startNum, int count) {
            String[] strArr = new String[count];
            int i = startNum;
            while (i < startNum + count) {
                strArr[i - startNum] = (i < 10 ? "0" : "") + i;
                i++;
            }
            return Arrays.asList(strArr);
        }

        private final int[] getCurrDateValues() {
            int parseInt = Integer.parseInt(this.params.loopYear.getCurrentItemValue());
            int parseInt2 = Integer.parseInt(this.params.loopMonth.getCurrentItemValue());
            int parseInt3 = Integer.parseInt(this.params.loopDay.getCurrentItemValue());
            return new int[]{parseInt, parseInt2, parseInt3};
        }

        public DatePickerDialog create() {
            final DatePickerDialog datePickerDialog = new DatePickerDialog(this.context, this.params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            View inflate = LayoutInflater.from(this.context).inflate(R.layout.layout_picker_date, null);
            inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    datePickerDialog.dismiss();
                    Builder.this.params.callback.onCancel();
                }
            });
            Calendar instance = Calendar.getInstance();
            final LoopView loopView = (LoopView) inflate.findViewById(R.id.loop_day);
            loopView.setArrayList(m33d(1, 30));
            if (this.selectDay != null) {
                loopView.setCurrentItem(this.selectDay.intValue());
            } else {
                loopView.setCurrentItem(instance.get(5));
            }
            final LoopView loopView2 = (LoopView) inflate.findViewById(R.id.loop_year);
            loopView2.setArrayList(m33d(DatePickerDialog.MIN_YEAR, (DatePickerDialog.MAX_YEAR - DatePickerDialog.MIN_YEAR) + 1));
            if (this.selectYear != null) {
                loopView2.setCurrentItem((this.selectYear.intValue() - DatePickerDialog.MIN_YEAR) + 1);
            } else {
                loopView2.setCurrentItem(DatePickerDialog.MAX_YEAR);
            }
            loopView2.setNotLoop();
            final LoopView loopView3 = (LoopView) inflate.findViewById(R.id.loop_month);
            loopView3.setArrayList(m33d(1, 12));
            if (this.selectMonth != null) {
                loopView3.setCurrentItem(this.selectMonth.intValue());
            } else {
                loopView3.setCurrentItem(instance.get(2));
            }
            loopView3.setNotLoop();
            LoopListener c04632 = new LoopListener() {
                public void onItemSelect(int item) {
                    Calendar instance = Calendar.getInstance();
                    if (Builder.this.minYear != null) {
                        if (Integer.parseInt(loopView2.getCurrentItemValue()) == Builder.this.minYear.intValue()) {
                            if (Builder.this.minMonth != null && Integer.parseInt(loopView3.getCurrentItemValue()) < Builder.this.minMonth.intValue()) {
                                loopView3.setCurrentItem(Builder.this.minMonth.intValue() - 1);
                            }
                        } else if (Integer.parseInt(loopView2.getCurrentItemValue()) < Builder.this.minYear.intValue()) {
                            loopView2.setCurrentItem(Builder.this.minYear.intValue() - DatePickerDialog.MIN_YEAR);
                        }
                    }
                    if (Builder.this.maxYear != null) {
                        if (Integer.parseInt(loopView2.getCurrentItemValue()) == Builder.this.maxYear.intValue()) {
                            if (Builder.this.maxMonth != null && Integer.parseInt(loopView3.getCurrentItemValue()) > Builder.this.maxMonth.intValue()) {
                                loopView3.setCurrentItem(Builder.this.maxMonth.intValue() - 1);
                            }
                        } else if (Integer.parseInt(loopView2.getCurrentItemValue()) > Builder.this.maxYear.intValue()) {
                            loopView2.setCurrentItem(Builder.this.maxYear.intValue() - DatePickerDialog.MIN_YEAR);
                        }
                    }
                    instance.set(Integer.parseInt(loopView2.getCurrentItemValue()), Integer.parseInt(loopView3.getCurrentItemValue()) - 1, 1);
                    instance.roll(5, false);
                    if (1 != 0) {
                        int i = instance.get(5);
                        int currentItem = loopView.getCurrentItem();
                        loopView.setArrayList(Builder.m33d(1, i));
                        if (currentItem > i) {
                            currentItem = i - 1;
                        }
                        loopView.setCurrentItem(currentItem);
                    }
                }
            };
            LoopListener c04643 = new LoopListener() {
                public void onItemSelect(int item) {
                    if (Builder.this.minYear != null && Builder.this.minMonth != null && Builder.this.minDay != null && Integer.parseInt(loopView2.getCurrentItemValue()) == Builder.this.minYear.intValue() && Integer.parseInt(loopView3.getCurrentItemValue()) == Builder.this.minMonth.intValue() && Integer.parseInt(loopView.getCurrentItemValue()) < Builder.this.minDay.intValue()) {
                        loopView.setCurrentItem(Builder.this.minDay.intValue() - 1);
                    }
                    if (Builder.this.maxYear != null && Builder.this.maxMonth != null && Builder.this.maxDay != null && Integer.parseInt(loopView2.getCurrentItemValue()) == Builder.this.maxYear.intValue() && Integer.parseInt(loopView3.getCurrentItemValue()) == Builder.this.maxMonth.intValue() && Integer.parseInt(loopView.getCurrentItemValue()) > Builder.this.maxDay.intValue()) {
                        loopView.setCurrentItem(Builder.this.maxDay.intValue() - 1);
                    }
                }
            };
            loopView2.setListener(c04632);
            loopView3.setListener(c04632);
            loopView.setListener(c04643);
            inflate.findViewById(R.id.tx_finish).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    datePickerDialog.dismiss();
                    Builder.this.params.callback.onDateSelected(Builder.this.getCurrDateValues());
                }
            });
            Window window = datePickerDialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            LayoutParams attributes = window.getAttributes();
            attributes.width = -1;
            attributes.height = -2;
            window.setAttributes(attributes);
            window.setGravity(80);
            window.setWindowAnimations(R.style.Animation_Bottom_Rising);
            datePickerDialog.setContentView(inflate);
            datePickerDialog.setCanceledOnTouchOutside(this.params.canCancel);
            datePickerDialog.setCancelable(this.params.canCancel);
            this.params.loopYear = loopView2;
            this.params.loopMonth = loopView3;
            this.params.loopDay = loopView;
            datePickerDialog.setParams(this.params);
            return datePickerDialog;
        }

        public Builder setMaxDay(int day) {
            this.maxDay = Integer.valueOf(day);
            return this;
        }

        public Builder setMaxMonth(int month) {
            this.maxMonth = Integer.valueOf(month);
            return this;
        }

        public Builder setMaxYear(int year) {
            this.maxYear = Integer.valueOf(year);
            return this;
        }

        public Builder setMinDay(int day) {
            this.minDay = Integer.valueOf(day);
            return this;
        }

        public Builder setMinMonth(int month) {
            this.minMonth = Integer.valueOf(month);
            return this;
        }

        public Builder setMinYear(int year) {
            this.minYear = Integer.valueOf(year);
            return this;
        }

        public Builder setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
            this.params.callback = onDateSelectedListener;
            return this;
        }

        public Builder setSelectDay(int day) {
            this.selectDay = Integer.valueOf(day);
            return this;
        }

        public Builder setSelectMonth(int month) {
            this.selectMonth = Integer.valueOf(month);
            return this;
        }

        public Builder setSelectYear(int year) {
            this.selectYear = Integer.valueOf(year);
            return this;
        }
    }

    public interface OnDateSelectedListener {
        void onCancel();

        void onDateSelected(int[] iArr);
    }

    private static final class Params {
        private OnDateSelectedListener callback;
        private boolean canCancel;
        private LoopView loopDay;
        private LoopView loopMonth;
        private LoopView loopYear;
        private boolean shadow;

        private Params() {
            this.shadow = true;
            this.canCancel = true;
        }
    }

    public DatePickerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void setParams(Params params) {
        this.params = params;
    }
}