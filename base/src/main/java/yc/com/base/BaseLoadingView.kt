package yc.com.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import primary.answer.yc.com.base.R


/**
 *
 * 加载进度条
 */

class BaseLoadingView(context: Context) : Dialog(context, R.style.LoadingProgressDialogStyle) {

    private val mTextView: TextView
    private var isBack = false
    private val mAnimationDrawable: AnimationDrawable?
    private val mIvProgressBar: ImageView?

    private var mOnDialogBackListener: OnDialogBackListener? = null

    private val mHandler = Handler()


    init {
        setContentView(R.layout.dialog_progress_layout)
        mTextView = findViewById(R.id.tv_loading_message)
        mIvProgressBar = findViewById(R.id.iv_loading_icon)
        mAnimationDrawable = mIvProgressBar.drawable as AnimationDrawable

        mIvProgressBar.visibility = View.VISIBLE
        if (!mAnimationDrawable.isRunning)
            mAnimationDrawable.start()

        //        setCancelable(false);
        //        setCanceledOnTouchOutside(false);

    }


    override fun dismiss() {
        if (null != mAnimationDrawable && mAnimationDrawable.isRunning) mAnimationDrawable.stop()
        if (null != mIvProgressBar) mIvProgressBar.visibility = View.VISIBLE

        super.dismiss()
    }

    interface OnDialogBackListener {
        fun onBack()
    }

    fun setOnDialogBackListener(onDialogBackListener: OnDialogBackListener) {
        mOnDialogBackListener = onDialogBackListener
    }

    /**
     * 将用户按下返回键时间传递出去
     *
     * @param keyCode
     * @param event
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return if (!isBack) {
                if (mOnDialogBackListener != null) {
                    mOnDialogBackListener!!.onBack()
                }
                false
            } else {
                super.onKeyDown(keyCode, event)
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 设置文字
     *
     * @param message
     */
    fun setMessage(message: String) {
        mTextView.text = message
    }

    /**
     * 设置文字，是否完成加载，自动关闭时间
     *
     * @param message
     * @param isFinlish
     * @param duration
     */
    fun setMessage(message: String, isFinlish: Boolean, duration: Int) {
        mTextView.text = message
        if (isFinlish) {
            if (mIvProgressBar != null) {
                mIvProgressBar.visibility = View.GONE
                if (null != mAnimationDrawable && mAnimationDrawable.isRunning)
                    mAnimationDrawable.stop()
                mHandler.postDelayed({ dismiss() }, duration.toLong())
            }
        }
    }

    /**
     * 设置点击空白处是否关闭Dialog
     */
    fun onSetCancelable(isClose: Boolean) {
        setCancelable(isClose)
    }

    /**
     * 设置返回键是否可用
     */
    fun onSetCanceledOnTouchOutside(isBack: Boolean) {
        this.isBack = isBack
        setCanceledOnTouchOutside(isBack)
    }

    /**
     * 执行完成播放动画的动作
     */
    fun setResultsCompletes(message: String, textColor: Int, isFinlish: Boolean, duration: Int) {
        mTextView.text = message
        mTextView.setTextColor(Color.WHITE)
        if (isFinlish) {
            if (mIvProgressBar != null) {
                mIvProgressBar.visibility = View.GONE
                if (null != mAnimationDrawable && mAnimationDrawable.isRunning)
                    mAnimationDrawable.stop()
                mHandler.postDelayed({ this@BaseLoadingView.dismiss() }, duration.toLong())
            }
        }
    }
}
