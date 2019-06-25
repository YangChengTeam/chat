package yc.com.chat.constellation.datapicker;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yc.com.chat.R;

public class DataPickerDialog extends Dialog {
    private Params params;

    public static class Builder {
        private final Context context;
        private final Params params = new Params();

        public Builder(Context context) {
            this.context = context;
        }

        private final String getCurrDateValue() {
            return this.params.loopData.getCurrentItemValue();
        }

        public DataPickerDialog create() {
            final DataPickerDialog dataPickerDialog = new DataPickerDialog(this.context, this.params.shadow ? R.style.Theme_Light_NoTitle_Dialog : R.style.Theme_Light_NoTitle_NoShadow_Dialog);
            View inflate = LayoutInflater.from(this.context).inflate(R.layout.layout_picker_data, null);
            if (!TextUtils.isEmpty(this.params.title)) {
                TextView textView = inflate.findViewById(R.id.tx_title);
                textView.setText(this.params.title);
                textView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dataPickerDialog.dismiss();
                        Builder.this.params.callback.onCancel();
                    }
                });
            }
            if (!TextUtils.isEmpty(this.params.unit)) {
                ((TextView) inflate.findViewById(R.id.tx_unit)).setText(this.params.unit);
            }
            final LoopView loopView = inflate.findViewById(R.id.loop_data);
            loopView.setArrayList(this.params.dataList);
            loopView.setNotLoop();
            if (this.params.dataList.size() > 0) {
                loopView.setCurrentItem(this.params.initSelection);
            }
            inflate.findViewById(R.id.tx_finish).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dataPickerDialog.dismiss();
                    Builder.this.params.callback.onDataSelected(Builder.this.getCurrDateValue(), loopView.getCurrentItem());
                }
            });
            Window window = dataPickerDialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            LayoutParams attributes = window.getAttributes();
            attributes.width = -1;
            attributes.height = -2;
            window.setAttributes(attributes);
            window.setGravity(80);
            window.setWindowAnimations(R.style.Animation_Bottom_Rising);
            dataPickerDialog.setContentView(inflate);
            dataPickerDialog.setCanceledOnTouchOutside(this.params.canCancel);
            dataPickerDialog.setCancelable(this.params.canCancel);
            this.params.loopData = loopView;
            dataPickerDialog.setParams(this.params);
            return dataPickerDialog;
        }

        public Builder setData(List<String> list) {
            this.params.dataList.clear();
            this.params.dataList.addAll(list);
            return this;
        }

        public Builder setOnDataSelectedListener(OnDataSelectedListener onDataSelectedListener) {
            this.params.callback = onDataSelectedListener;
            return this;
        }

        public Builder setSelection(int selection) {
            this.params.initSelection = selection;
            return this;
        }

        public Builder setTitle(String title) {
            this.params.title = title;
            return this;
        }

        public Builder setUnit(String unit) {
            this.params.unit = unit;
            return this;
        }
    }

    public interface OnDataSelectedListener {
        void onCancel();

        void onDataSelected(String str, int i);
    }

    private static final class Params {
        private OnDataSelectedListener callback;
        private boolean canCancel;
        private final List<String> dataList;
        private int initSelection;
        private LoopView loopData;
        private boolean shadow;
        private String title;
        private String unit;

        private Params() {
            this.shadow = true;
            this.canCancel = true;
            this.dataList = new ArrayList();
        }
    }

    public DataPickerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void setParams(Params params) {
        this.params = params;
    }

    public void setSelection(String itemValue) {
        if (this.params.dataList.size() > 0) {
            int indexOf = this.params.dataList.indexOf(itemValue);
            if (indexOf >= 0) {
                this.params.initSelection = indexOf;
                this.params.loopData.setCurrentItem(this.params.initSelection);
            }
        }
    }
}