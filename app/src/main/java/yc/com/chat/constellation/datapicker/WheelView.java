package yc.com.chat.constellation.datapicker;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import yc.com.chat.R;

public class WheelView extends View {
    private static final int DEF_VISIBLE_ITEMS = 5;
    private static final int ITEM_OFFSET_PERCENT = 10;
    private static final int PADDING = 10;
    private static final int[] SHADOWS_COLORS = new int[]{-15658735, 11184810, 11184810};
    private static final String TAG = "WheelView";
    private GradientDrawable bottomShadow;
    private Drawable centerDrawable;
    private List<OnWheelChangedListener> changingListeners = new LinkedList();
    private List<OnWheelClickedListener> clickingListeners = new LinkedList();
    private int currentItem = 0;
    private DataSetObserver dataObserver = new C04572();
    private int firstItem;
    boolean isCyclic = false;
    private boolean isScrollingPerformed;
    private int itemHeight = 0;
    private LinearLayout itemsLayout;
    private WheelRecycle recycle = new WheelRecycle(this);
    private WheelScroller scroller;
    WheelScroller.ScrollingListener scrollingListener = new C04561();
    private List<OnWheelScrollListener> scrollingListeners = new LinkedList();
    private int scrollingOffset;
    int selectTextColor;
    int textColor;
    private GradientDrawable topShadow;
    private WheelViewAdapter viewAdapter;
    private int visibleItems = 5;

    /* renamed from: com.codbking.widget.view.WheelView$1 */
    class C04561 implements WheelScroller.ScrollingListener {
        C04561() {
        }

        public void onFinished() {
            if (WheelView.this.isScrollingPerformed) {
                WheelView.this.notifyScrollingListenersAboutEnd();
                WheelView.this.isScrollingPerformed = false;
            }
            WheelView.this.scrollingOffset = 0;
            WheelView.this.invalidate();
        }

        public void onJustify() {
            if (Math.abs(WheelView.this.scrollingOffset) > 1) {
                WheelView.this.scroller.scroll(WheelView.this.scrollingOffset, 0);
            }
        }

        public void onScroll(int distance) {
            WheelView.this.doScroll(distance);
            int height = WheelView.this.getHeight();
            if (WheelView.this.scrollingOffset > height) {
                WheelView.this.scrollingOffset = height;
                WheelView.this.scroller.stopScrolling();
            } else if (WheelView.this.scrollingOffset < (-height)) {
                WheelView.this.scrollingOffset = -height;
                WheelView.this.scroller.stopScrolling();
            }
        }

        public void onStarted() {
            WheelView.this.isScrollingPerformed = true;
            WheelView.this.notifyScrollingListenersAboutStart();
        }
    }

    /* renamed from: com.codbking.widget.view.WheelView$2 */
    class C04572 extends DataSetObserver {
        C04572() {
        }

        public void onChanged() {
            WheelView.this.invalidateWheel(false);
        }

        public void onInvalidated() {
            WheelView.this.invalidateWheel(true);
        }
    }

    public WheelView(Context context) {
        super(context);
        initData(context);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData(context);
    }

    private boolean addViewItem(int index, boolean first) {
        View itemView = getItemView(index);
        refreshTextStatus(itemView, index);
        if (itemView == null) {
            return false;
        }
        if (first) {
            this.itemsLayout.addView(itemView, 0);
        } else {
            this.itemsLayout.addView(itemView);
        }
        return true;
    }

    private void buildViewForMeasuring() {
        if (this.itemsLayout != null) {
            this.recycle.recycleItems(this.itemsLayout, this.firstItem, new ItemsRange());
        } else {
            createItemsLayout();
        }
        int i = this.visibleItems / 2;
        for (int i2 = this.currentItem + i; i2 >= this.currentItem - i; i2--) {
            if (addViewItem(i2, true)) {
                this.firstItem = i2;
            }
        }
    }

    private int calculateLayoutWidth(int widthSize, int mode) {
        initResourcesIfNecessary();
        this.itemsLayout.setLayoutParams(new LayoutParams(-2, -2));
        this.itemsLayout.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int measuredWidth = this.itemsLayout.getMeasuredWidth();
        if (mode == 1073741824) {
            measuredWidth = widthSize;
        } else {
            measuredWidth = Math.max(measuredWidth + 20, getSuggestedMinimumWidth());
            if (mode == Integer.MIN_VALUE && widthSize < measuredWidth) {
                measuredWidth = widthSize;
            }
        }
        this.itemsLayout.measure(MeasureSpec.makeMeasureSpec(measuredWidth - 20, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        return measuredWidth;
    }

    private void createItemsLayout() {
        if (this.itemsLayout == null) {
            this.itemsLayout = new LinearLayout(getContext());
            this.itemsLayout.setOrientation(LinearLayout.VERTICAL);
        }
    }

    private void doScroll(int delta) {
        this.scrollingOffset += delta;
        int itemHeight = getItemHeight();
        int i = this.scrollingOffset / itemHeight;
        int i2 = this.currentItem - i;
        int itemsCount = this.viewAdapter.getItemsCount();
        int i3 = this.scrollingOffset % itemHeight;
        if (Math.abs(i3) <= itemHeight / 2) {
            i3 = 0;
        }
        if (this.isCyclic && itemsCount > 0) {
            if (i3 > 0) {
                i2--;
                i++;
            } else if (i3 < 0) {
                i2++;
                i--;
            }
            while (i2 < 0) {
                i2 += itemsCount;
            }
            i2 %= itemsCount;
        } else if (i2 < 0) {
            i = this.currentItem;
            i2 = 0;
        } else if (i2 >= itemsCount) {
            i = (this.currentItem - itemsCount) + 1;
            i2 = itemsCount - 1;
        } else if (i2 > 0 && i3 > 0) {
            i2--;
            i++;
        } else if (i2 < itemsCount - 1 && i3 < 0) {
            i2++;
            i--;
        }
        int i4 = this.scrollingOffset;
        if (i2 != this.currentItem) {
            setCurrentItem(i2, false);
        } else {
            invalidate();
        }
        this.scrollingOffset = i4 - (i * itemHeight);
        if (this.scrollingOffset > getHeight()) {
            this.scrollingOffset = (this.scrollingOffset % getHeight()) + getHeight();
        }
    }

    private void drawCenterRect(Canvas canvas) {
        int height = getHeight() / 2;
        int itemHeight = (int) (((double) (getItemHeight() / 2)) * 1.2d);
    }

    private void drawItems(Canvas canvas) {
        canvas.save();
        canvas.translate(10.0f, (float) ((-(((this.currentItem - this.firstItem) * getItemHeight()) + ((getItemHeight() - getHeight()) / 2))) + this.scrollingOffset));
        this.itemsLayout.draw(canvas);
        canvas.restore();
    }

    private void drawShadows(Canvas canvas) {
        int itemHeight = (int) (1.5d * ((double) getItemHeight()));
        this.topShadow.setBounds(0, 0, getWidth(), itemHeight);
        this.topShadow.draw(canvas);
        this.bottomShadow.setBounds(0, getHeight() - itemHeight, getWidth(), getHeight());
        this.bottomShadow.draw(canvas);
    }

    private int getDesiredHeight(LinearLayout layout) {
        if (!(layout == null || layout.getChildAt(0) == null)) {
            this.itemHeight = layout.getChildAt(0).getMeasuredHeight();
        }
        return Math.max((this.itemHeight * this.visibleItems) - ((this.itemHeight * 10) / 50), getSuggestedMinimumHeight());
    }

    private View getItemView(int index) {
        if (this.viewAdapter == null || this.viewAdapter.getItemsCount() == 0) {
            return null;
        }
        int itemsCount = this.viewAdapter.getItemsCount();
        if (!isValidItemIndex(index)) {
            return this.viewAdapter.getEmptyItem(this.recycle.getEmptyItem(), this.itemsLayout);
        }
        while (index < 0) {
            index += itemsCount;
        }
        return this.viewAdapter.getItem(index % itemsCount, this.recycle.getItem(), this.itemsLayout);
    }

    private ItemsRange getItemsRange() {
        if (getItemHeight() == 0) {
            return null;
        }
        int i = this.currentItem;
        int i2 = 1;
        while (getItemHeight() * i2 < getHeight()) {
            i--;
            i2 += 2;
        }
        if (this.scrollingOffset != 0) {
            if (this.scrollingOffset > 0) {
                i--;
            }
            int itemHeight = this.scrollingOffset / getItemHeight();
            i -= itemHeight;
            i2 = (int) (((double) (i2 + 1)) + Math.asin((double) itemHeight));
        }
        return new ItemsRange(i, i2);
    }

    private void initData(Context context) {
        this.scroller = new WheelScroller(getContext(), this.scrollingListener);
    }

    private void initResourcesIfNecessary() {
        if (this.topShadow == null) {
            this.topShadow = new GradientDrawable(Orientation.TOP_BOTTOM, SHADOWS_COLORS);
        }
        if (this.bottomShadow == null) {
            this.bottomShadow = new GradientDrawable(Orientation.BOTTOM_TOP, SHADOWS_COLORS);
        }
    }

    private boolean isValidItemIndex(int index) {
        return this.viewAdapter != null && this.viewAdapter.getItemsCount() > 0 && (this.isCyclic || (index >= 0 && index < this.viewAdapter.getItemsCount()));
    }

    private void layout(int width, int height) {
        this.itemsLayout.layout(0, 0, width - 20, height);
    }

    private boolean rebuildItems() {
        int recycleItems;
        boolean z;
        int i;
        ItemsRange itemsRange = getItemsRange();
        if (this.itemsLayout != null) {
            recycleItems = this.recycle.recycleItems(this.itemsLayout, this.firstItem, itemsRange);
            z = this.firstItem != recycleItems;
            this.firstItem = recycleItems;
        } else {
            createItemsLayout();
            z = true;
        }
        if (!z) {
            z = (this.firstItem == itemsRange.getFirst() && this.itemsLayout.getChildCount() == itemsRange.getCount()) ? false : true;
        }
        if (this.firstItem <= itemsRange.getFirst() || this.firstItem > itemsRange.getLast()) {
            this.firstItem = itemsRange.getFirst();
        } else {
            i = this.firstItem - 1;
            while (i >= itemsRange.getFirst() && addViewItem(i, true)) {
                this.firstItem = i;
                i--;
            }
        }
        recycleItems = this.firstItem;
        for (i = this.itemsLayout.getChildCount(); i < itemsRange.getCount(); i++) {
            if (!addViewItem(this.firstItem + i, false) && this.itemsLayout.getChildCount() == 0) {
                recycleItems++;
            }
        }
        this.firstItem = recycleItems;
        return z;
    }

    private void refreshTextStatus(View oldView, int old) {
        if (oldView != null) {
            TextView textView = oldView.findViewById(R.id.text);
            if (old == this.currentItem) {
                textView.setTextColor(this.selectTextColor);
            } else {
                textView.setTextColor(this.textColor);
            }
        }
    }

    private void updateView() {
        if (rebuildItems()) {
            calculateLayoutWidth(getWidth(), 1073741824);
            layout(getWidth(), getHeight());
        }
    }

    public void addChangingListener(OnWheelChangedListener listener) {
        this.changingListeners.add(listener);
    }

    public void addClickingListener(OnWheelClickedListener listener) {
        this.clickingListeners.add(listener);
    }

    public void addScrollingListener(OnWheelScrollListener listener) {
        this.scrollingListeners.add(listener);
    }

    public int getCurrentItem() {
        return this.currentItem;
    }

    public int getItemHeight() {
        if (this.itemHeight != 0) {
            return this.itemHeight;
        }
        if (this.itemsLayout == null || this.itemsLayout.getChildAt(0) == null) {
            return getHeight() / this.visibleItems;
        }
        this.itemHeight = this.itemsLayout.getChildAt(0).getHeight();
        return this.itemHeight;
    }

    public WheelViewAdapter getViewAdapter() {
        return this.viewAdapter;
    }

    public int getVisibleItems() {
        return this.visibleItems;
    }

    public void invalidateWheel(boolean clearCaches) {
        if (clearCaches) {
            this.recycle.clearAll();
            if (this.itemsLayout != null) {
                this.itemsLayout.removeAllViews();
            }
            this.scrollingOffset = 0;
        } else if (this.itemsLayout != null) {
            this.recycle.recycleItems(this.itemsLayout, this.firstItem, new ItemsRange());
        }
        invalidate();
    }

    public boolean isCyclic() {
        return this.isCyclic;
    }

    public boolean isScrollingPerformed() {
        return this.isScrollingPerformed;
    }

    protected void notifyChangingListeners(int oldValue, int newValue) {
        for (OnWheelChangedListener onChanged : this.changingListeners) {
            onChanged.onChanged(this, oldValue, newValue);
        }
        if (oldValue >= 0 && newValue >= 0 && this.itemsLayout != null) {
            View childAt = this.itemsLayout.getChildAt(oldValue - this.firstItem);
            View childAt2 = this.itemsLayout.getChildAt(newValue - this.firstItem);
            refreshTextStatus(childAt, oldValue);
            refreshTextStatus(childAt2, newValue);
        }
    }

    protected void notifyClickListenersAboutClick(int item) {
        for (OnWheelClickedListener onItemClicked : this.clickingListeners) {
            onItemClicked.onItemClicked(this, item);
        }
    }

    protected void notifyScrollingListenersAboutEnd() {
        for (OnWheelScrollListener onScrollingFinished : this.scrollingListeners) {
            onScrollingFinished.onScrollingFinished(this);
        }
    }

    protected void notifyScrollingListenersAboutStart() {
        for (OnWheelScrollListener onScrollingStarted : this.scrollingListeners) {
            onScrollingStarted.onScrollingStarted(this);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.viewAdapter != null && this.viewAdapter.getItemsCount() > 0) {
            updateView();
            drawItems(canvas);
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layout(r - l, b - t);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int size2 = MeasureSpec.getSize(heightMeasureSpec);
        buildViewForMeasuring();
        int calculateLayoutWidth = calculateLayoutWidth(size, mode);
        if (mode2 == 1073741824) {
            i = size2;
        } else {
            i = getDesiredHeight(this.itemsLayout);
            if (mode2 == Integer.MIN_VALUE) {
                i = Math.min(i, size2);
            }
        }
        setMeasuredDimension(calculateLayoutWidth, i);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || getViewAdapter() == null) {
            return true;
        }
        switch (event.getAction()) {
            case 1:
                if (!this.isScrollingPerformed) {
                    int y = ((int) event.getY()) - (getHeight() / 2);
                    int itemHeight = (y > 0 ? y + (getItemHeight() / 2) : y - (getItemHeight() / 2)) / getItemHeight();
                    if (itemHeight != 0 && isValidItemIndex(this.currentItem + itemHeight)) {
                        notifyClickListenersAboutClick(this.currentItem + itemHeight);
                        break;
                    }
                }
                break;
            case 2:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                }
                break;
        }
        return this.scroller.onTouchEvent(event);
    }

    public void removeChangingListener(OnWheelChangedListener listener) {
        this.changingListeners.remove(listener);
    }

    public void removeClickingListener(OnWheelClickedListener listener) {
        this.clickingListeners.remove(listener);
    }

    public void removeScrollingListener(OnWheelScrollListener listener) {
        this.scrollingListeners.remove(listener);
    }

    public void scroll(int itemsToScroll, int time) {
        this.scroller.scroll((getItemHeight() * itemsToScroll) - this.scrollingOffset, time);
    }

    public void setCurrentItem(int index) {
        setCurrentItem(index, false);
    }

    public void setCurrentItem(int index, boolean animated) {
        if (this.viewAdapter != null && this.viewAdapter.getItemsCount() != 0) {
            int itemsCount = this.viewAdapter.getItemsCount();
            if (index < 0 || index >= itemsCount) {
                if (this.isCyclic) {
                    while (index < 0) {
                        index += itemsCount;
                    }
                    index %= itemsCount;
                } else {
                    return;
                }
            }
            if (index == this.currentItem) {
                return;
            }
            if (animated) {
                int i = index - this.currentItem;
                if (this.isCyclic) {
                    int min = (Math.min(index, this.currentItem) + itemsCount) - Math.max(index, this.currentItem);
                    if (min < Math.abs(i)) {
                        i = i < 0 ? min : -min;
                    }
                }
                scroll(i, 0);
                return;
            }
            this.scrollingOffset = 0;
            int i2 = this.currentItem;
            this.currentItem = index;
            notifyChangingListeners(i2, this.currentItem);
            invalidate();
        }
    }

    public void setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;
        invalidateWheel(false);
    }

    public void setInterpolator(Interpolator interpolator) {
        this.scroller.setInterpolator(interpolator);
    }

    public void setSelectTextColor(int textColor, int selectTextColor) {
        this.selectTextColor = selectTextColor;
        this.textColor = textColor;
    }

    public void setViewAdapter(WheelViewAdapter viewAdapter) {
        if (this.viewAdapter != null) {
            this.viewAdapter.unregisterDataSetObserver(this.dataObserver);
        }
        this.viewAdapter = viewAdapter;
        if (this.viewAdapter != null) {
            this.viewAdapter.registerDataSetObserver(this.dataObserver);
        }
        invalidateWheel(true);
    }

    public void setVisibleItems(int count) {
        this.visibleItems = count;
    }

    public void stopScrolling() {
        this.scroller.stopScrolling();
    }
}