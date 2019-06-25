package yc.com.chat.constellation.datapicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import yc.com.chat.R;

public class GenWheelText extends GenWheelView {
    private int line;
    private int textColor;
    private int textSize;

    private class ViewHolder {
        public TextView text;

        private ViewHolder() {
        }
    }

    public GenWheelText() {
        this(1, 24, 4473924);
    }

    public GenWheelText(int textColor) {
        this(1, 24, textColor);
    }

    public GenWheelText(int line, int textSize, int textColor) {
        this.line = 1;
        this.textSize = 24;
        this.line = line;
        this.textSize = textSize;
        this.textColor = textColor;
    }

    protected View genBody(Context context, View convertView, Object element, int position) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.cbk_wheel_default_inner_text, null);
            viewHolder = new ViewHolder();
            viewHolder.text = convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }
        viewHolder.text.setTextSize((float) this.textSize);
        viewHolder.text.setMaxLines(this.line);
        viewHolder.text.setText(element.toString());
        viewHolder.text.setTextColor(this.textColor);
        return convertView;
    }
}