package yc.com.chat.combat.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 *
 * Created by wanglin  on 2019/6/24 14:38.
 */
class MyScrollView(context: Context, attributeSet: AttributeSet) : ScrollView(context, attributeSet) {


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        mListener?.onScrolled(l, t, oldl, oldt)
    }

    var mListener: OnScrollChangedListener? = null

    fun setOnScrollChangedListener(listener: OnScrollChangedListener) {
        this.mListener = listener
    }

    interface OnScrollChangedListener {
        fun onScrolled(l: Int, t: Int, oldl: Int, oldt: Int)
    }

}