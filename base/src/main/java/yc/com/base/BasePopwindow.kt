package yc.com.base

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.PopupWindow
import com.vondear.rxtools.RxBarTool

/**
 * Created by wanglin  on 2018/3/8 16:43.
 */

abstract class BasePopwindow(protected var mContext: Activity) : PopupWindow(), IView {
    private var mBackgroundDrawable: ColorDrawable? = null

    abstract val animationID: Int

    init {

        val inflater = mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contextView = inflater.inflate(getLayoutId(), null)
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        contentView = contextView
        setWindowAlpha(1f)


        val aid = animationID
        if (aid != 0) {
            animationStyle = aid
        }

//        try {
//            ButterKnife.bind(this, contextView)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            RxLogTool.i(this.javaClass.simpleName + " ButterKnife->初始化失败 原因:" + e)
//        }

        init()
    }


    private fun setWindowAlpha(alpha: Float) {
        val window = mContext.window
        val lp = window.attributes
        lp.alpha = alpha
        mContext.window.attributes = lp
    }

    override fun dismiss() {
        super.dismiss()


        UIUtils.postDelay(Runnable { setWindowAlpha(1f) }, 300)
    }


    override fun setContentView(contentView: View?) {
        if (contentView != null) {
            super.setContentView(contentView)
            isFocusable = true
            isTouchable = true
            contentView.isFocusable = true
            contentView.isFocusableInTouchMode = true
            contentView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                when (keyCode) {
                    KeyEvent.KEYCODE_BACK -> {
                        dismiss()
                        return@OnKeyListener true
                    }
                    else -> {
                    }
                }
                false
            })
        }
    }

    @JvmOverloads
    fun show(view: View = mContext.window.decorView.rootView, gravity: Int = Gravity.BOTTOM) {

        showAtLocation(view, gravity, 0, RxBarTool.getStatusBarHeight(mContext))
    }

    override fun setOutsideTouchable(touchable: Boolean) {
        super.setOutsideTouchable(touchable)
        if (touchable) {
            if (mBackgroundDrawable == null) {
                mBackgroundDrawable = ColorDrawable(0x00000000)
            }
            super.setBackgroundDrawable(mBackgroundDrawable)
        } else {
            super.setBackgroundDrawable(null)
        }
    }
}
