package yc.com.base

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.umeng.analytics.MobclickAgent
import primary.answer.yc.com.base.R

/**
 * Created by wanglin  on 2018/3/6 10:14.
 */

abstract class BaseActivity<P : BasePresenter<*, *>> : AppCompatActivity(), IView, IDialog {


    protected var mPresenter: P? = null
    protected var baseLoadingView: BaseLoadingView? = null
    protected var mHandler: Handler? = null
    private var taskRunnable: MyRunnable? = null

    var statusColor = Color.TRANSPARENT

    /**
     * 定时任务，模拟倒计时广告
     */
    private var totalTime = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        baseLoadingView = BaseLoadingView(this)
        mHandler = Handler()

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window = window
//
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.statusBarColor = statusColor
//        }
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit)
        if (isStatusBarMateria())
        //        //顶部透明
            setStatusBarMateria()
        init()
    }

    private fun setStatusBarMateria() {
        StatusBarUtil.setMaterialStatus(this)
    }

    open fun isStatusBarMateria(): Boolean {
        return true
    }


    override fun onResume() {
        super.onResume()

        MobclickAgent.onResume(this)
        if (EmptyUtils.isNotEmpty(mPresenter)) {
            mPresenter?.subscribe()
        }
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }


    override fun onDestroy() {
        super.onDestroy()

        if (EmptyUtils.isNotEmpty(mPresenter)) {
            mPresenter?.unsubscribe()
        }
        mHandler?.removeCallbacks(taskRunnable)
        taskRunnable = null
        mHandler = null
    }

    override fun showLoadingDialog(mess: String) {
        if (!this.isFinishing) {
            if (null != baseLoadingView) {
                baseLoadingView?.setMessage(mess)
                baseLoadingView?.show()
            }
        }
    }

    override fun dismissDialog() {
        try {

            if (!this.isFinishing) {
                if (null != baseLoadingView && baseLoadingView!!.isShowing) {
                    baseLoadingView?.dismiss()
                }
            }
        } catch (e: Exception) {

        }

    }

    /**
     * 改变获取验证码按钮状态
     */
    fun showGetCodeDisplay(textView: TextView) {
        taskRunnable = MyRunnable(textView)
        if (null != mHandler) {
            mHandler?.removeCallbacks(taskRunnable)
            mHandler?.removeMessages(0)
            totalTime = 60
            textView.isClickable = false
            //            textView.setTextColor(ContextCompat.getColor(R.color.coment_color));
            //            textView.setBackgroundResource(R.drawable.bg_btn_get_code);
            if (null != mHandler) mHandler?.postDelayed(taskRunnable, 0)
        }
    }

    private inner class MyRunnable(internal var mTv: TextView) : Runnable {

        override fun run() {
            mTv.text = totalTime.toString() + "秒后重试"
            totalTime--
            if (totalTime < 0) {
                //还原
                initGetCodeBtn(mTv)
                return
            }
            if (null != mHandler) mHandler?.postDelayed(this, 1000)
        }
    }


    /**
     * 还原获取验证码按钮状态
     */
    private fun initGetCodeBtn(textView: TextView) {
        totalTime = 0
        if (null != taskRunnable && null != mHandler) {
            mHandler?.removeCallbacks(taskRunnable)
            mHandler?.removeMessages(0)
        }
        textView.text = "重新获取"
        textView.isClickable = true
        //        textView.setTextColor(CommonUtils.getColor(R.color.white));
        //        textView.setBackgroundResource(R.drawable.bg_btn_get_code_true);
    }


    protected fun setImmerseLayout(view: View) {// view为标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val statusBarHeight = getStatusBarHeight(this)
            view.setPadding(0, statusBarHeight, 0, 0)
        }
    }

    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen",
                "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}
