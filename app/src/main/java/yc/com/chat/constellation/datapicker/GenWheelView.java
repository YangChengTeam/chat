package yc.com.chat.constellation.datapicker;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

public abstract class GenWheelView implements IGenWheelView {
    protected abstract View genBody(Context context, View view, Object obj, int i);

    public View setup(Context context, int position, View convertView, ViewGroup parent, Object data) {
        if (data instanceof Object[]) {
            return genBody(context, convertView, ((Object[]) data)[position], position);
        }
        if (data instanceof ArrayList) {
            return genBody(context, convertView, ((ArrayList) data).get(position), position);
        }
        if (data instanceof LinkedHashMap) {
            int i = position;

            LinkedHashMap<Object, Object> result = (LinkedHashMap) data;
            for (Entry entry : result.entrySet()) {
                if (entry.getValue() instanceof List) {
                    if (i <= ((List) entry.getValue()).size()) {
                        return genBody(context, convertView, ((List) entry.getValue()).get(i - 1), position);
                    }
                    i = (i - ((List) entry.getValue()).size()) - 1;
                }
            }
            return null;
        } else if (!(data instanceof Cursor)) {
            return data instanceof SparseArray ? genBody(context, convertView, ((SparseArray) data).valueAt(position), position) : data instanceof SparseBooleanArray ? genBody(context, convertView, Boolean.valueOf(((SparseBooleanArray) data).get(((SparseBooleanArray) data).keyAt(position))), position) : data instanceof SparseIntArray ? genBody(context, convertView, Integer.valueOf(((SparseIntArray) data).valueAt(position)), position) : data instanceof Vector ? genBody(context, convertView, ((Vector) data).get(position), position) : data instanceof LinkedList ? genBody(context, convertView, ((LinkedList) data).get(position), position) : convertView;
        } else {
            ((Cursor) data).moveToPosition(position);
            return genBody(context, convertView, data, position);
        }
    }
}