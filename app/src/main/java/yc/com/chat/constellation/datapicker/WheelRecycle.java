package yc.com.chat.constellation.datapicker;

import android.view.View;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

public class WheelRecycle {
    private List<View> emptyItems;
    private List<View> items;
    private WheelView wheel;

    public WheelRecycle(WheelView wheel) {
        this.wheel = wheel;
    }

    private List<View> addView(View view, List<View> list) {
        if (list == null) {
            list = new LinkedList();
        }
        list.add(view);
        return list;
    }

    private View getCachedView(List<View> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        View view = (View) list.get(0);
        list.remove(0);
        return view;
    }

    private void recycleView(View view, int index) {
        int itemsCount = this.wheel.getViewAdapter().getItemsCount();
        if ((index < 0 || index >= itemsCount) && !this.wheel.isCyclic()) {
            this.emptyItems = addView(view, this.emptyItems);
            return;
        }
        while (index < 0) {
            index += itemsCount;
        }
        index %= itemsCount;
        this.items = addView(view, this.items);
    }

    public void clearAll() {
        if (this.items != null) {
            this.items.clear();
        }
        if (this.emptyItems != null) {
            this.emptyItems.clear();
        }
    }

    public View getEmptyItem() {
        return getCachedView(this.emptyItems);
    }

    public View getItem() {
        return getCachedView(this.items);
    }

    public int recycleItems(LinearLayout layout, int firstItem, ItemsRange range) {
        int i = firstItem;
        int i2 = 0;
        while (i2 < layout.getChildCount()) {
            if (range.contains(i)) {
                i2++;
            } else {
                recycleView(layout.getChildAt(i2), i);
                layout.removeViewAt(i2);
                if (i2 == 0) {
                    firstItem++;
                }
            }
            i++;
        }
        return firstItem;
    }
}