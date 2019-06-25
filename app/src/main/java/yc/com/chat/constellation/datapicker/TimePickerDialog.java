package yc.com.chat.constellation.datapicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import java.util.Arrays;
import java.util.List;

import yc.com.chat.R;

public class TimePickerDialog extends Dialog {
    private Params params;

    public static class Builder {
        private final Context context;
        private final Params params = new Params();

        public Builder(Context context) {
            this.context = context;
        }

        /* renamed from: d */
        private static List<String> m41d(int startNum, int count) {
            String[] strArr = new String[count];
            int i = startNum;
            while (i < startNum + count) {
                strArr[i - startNum] = (i < 10 ? "0" : "") + i;
                i++;
            }
            return Arrays.asList(strArr);
        }

        private final int[] getCurrDateValues() {
            int parseInt = Integer.parseInt(this.params.loopHour.getCurrentItemValue());
            int parseInt2 = Integer.parseInt(this.params.loopMin.getCurrentItemValue());
            return new int[]{parseInt, parseInt2};
        }

        public TimePickerDialog create() {
            final TimePickerDialog timePickerDialog = new TimePickerDialog(this.context, this.params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            View inflate = LayoutInflater.from(this.context).inflate(R.layout.layout_picker_time, null);
            LoopView loopView = inflate.findViewById(R.id.loop_hour);
            loopView.setCyclic(false);
            loopView.setArrayList(m41d(0, 24));
            loopView.setCurrentItem(12);
            LoopView loopView2 = inflate.findViewById(R.id.loop_min);
            loopView2.setCyclic(false);
            loopView2.setArrayList(m41d(0, 60));
            loopView2.setCurrentItem(30);
            inflate.findViewById(R.id.tx_finish).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timePickerDialog.dismiss();
                            Builder.this.params.callback.onTimeSelected(Builder.this.getCurrDateValues());
                        }
                    }
            );
            Window window = timePickerDialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            LayoutParams attributes = window.getAttributes();
            attributes.width = -1;
            attributes.height = -2;
            window.setAttributes(attributes);
            window.setGravity(80);
            window.setWindowAnimations(R.style.Animation_Bottom_Rising);
            timePickerDialog.setContentView(inflate);
            timePickerDialog.setCanceledOnTouchOutside(this.params.canCancel);
            timePickerDialog.setCancelable(this.params.canCancel);
            this.params.loopHour = loopView;
            this.params.loopMin = loopView2;
            timePickerDialog.setParams(this.params);
            return timePickerDialog;
        }

        public Builder setOnTimeSelectedListener(OnTimeSelectedListener onTimeSelectedListener) {
            this.params.callback = onTimeSelectedListener;
            return this;
        }
    }

    public interface OnTimeSelectedListener {
        void onTimeSelected(int[] iArr);
    }

    private static final class Params {
        private OnTimeSelectedListener callback;
        private boolean canCancel;
        private LoopView loopHour;
        private LoopView loopMin;
        private boolean shadow;

        private Params() {
            this.shadow = true;
            this.canCancel = true;
        }
    }

    public TimePickerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void setParams(Params params) {
        this.params = params;
    }
}