package yc.com.chat.base.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet

import com.kk.utils.LogUtil
import kotlinx.android.synthetic.main.view_main_item.view.*


import yc.com.base.BaseView
import yc.com.chat.R


/**
 * Created by wanglin  on 2018/11/20 09:40.
 */
class MainItemView(context: Context, attrs: AttributeSet?) : BaseView(context, attrs) {

    private var selectDrawable: Drawable? = null
    private var normalDrawable: Drawable? = null
    private var title: String? = null
    private var selectColor: Int = 0
    private var normalColor: Int = 0

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MainItemView)
        try {
            selectDrawable = ta.getDrawable(R.styleable.MainItemView_select_icon)
            normalDrawable = ta.getDrawable(R.styleable.MainItemView_normal_icon)

            title = ta.getString(R.styleable.MainItemView_title)
            selectColor = ta.getColor(R.styleable.MainItemView_select_titleColor, ContextCompat.getColor(context, R.color.main_color))
            normalColor = ta.getColor(R.styleable.MainItemView_normal_titleColor, ContextCompat.getColor(context, R.color._cccccc))


            if (!TextUtils.isEmpty(title)) {
                tv_item_name.text = title
            }
            setSelect(false)

            ta.recycle()
        } catch (e: Exception) {
            LogUtil.msg("mainItemView:->>" + e.message)
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.view_main_item
    }


    fun setDrawable(drawable: Drawable) {

        iv_item_icon.setImageDrawable(drawable)
    }

    fun setTitle(title: String) {
        this.title = title
        tv_item_name.text = title
    }


    fun setSelect(flag: Boolean) {
        if (flag) {
            if (selectDrawable != null)
                iv_item_icon.setImageDrawable(selectDrawable)
            tv_item_name.setTextColor(selectColor)

        } else {
            if (normalDrawable != null)
                iv_item_icon.setImageDrawable(normalDrawable)
            tv_item_name.setTextColor(normalColor)
        }
    }


}
