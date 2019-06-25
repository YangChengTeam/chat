package yc.com.chat.index.utils

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

import com.kk.utils.ScreenUtil

/**
 * Created by wanglin  on 2018/10/25 13:45.
 */
class ItemDecorationHelper(private val mContext: Context, left: Int, top: Int, right: Int, bottom: Int) : RecyclerView.ItemDecoration() {
    private var right = 0
    private var bottom = 0
    private var left = 0
    private var top = 0

    constructor(context: Context, right: Int, bottom: Int) : this(context, 0, 0, right, bottom) {}

    constructor(context: Context, bottom: Int) : this(context, 0, bottom) {}

    init {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(ScreenUtil.dip2px(mContext, left.toFloat()), ScreenUtil.dip2px(mContext, top.toFloat()), ScreenUtil.dip2px(mContext, right.toFloat()), ScreenUtil.dip2px(mContext, bottom.toFloat()))
    }
}
