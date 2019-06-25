package yc.com.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout

/**
 *
 * Created by wanglin  on 2019/6/6 16:31.
 */

abstract class BaseView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs), IView {
    protected var mContext: Context = context


    constructor(context: Context) : this(context, null)

    init {
        LayoutInflater.from(context).inflate(getLayoutId(), this)


        init()
    }

    override fun init() {
    }


}