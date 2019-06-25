package yc.com.chat.tools.activity

import android.os.Handler
import android.os.Message
import kotlinx.android.synthetic.main.activity_clock.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import java.util.*


/**
 *
 * Created by wanglin  on 2019/6/20 12:06.
 */
class ClockActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_clock
    }

    override fun init() {
        TimeThread().start()
    }

    private var isTure = true
    private val myHandler = MyHandler()


    /* renamed from: com.rjjmc.newproinlove.activity.ClockActivity$1 */
    internal inner class MyHandler : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    this@ClockActivity.getTime()
                    return
                }
                else -> return
            }
        }
    }

    inner class TimeThread : Thread() {

        override fun run() {
            while (this@ClockActivity.isTure) {
                try {
                    Thread.sleep(100)
                    val message = Message()
                    message.what = 1
                    this@ClockActivity.myHandler.sendMessage(message)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun getTime() {
        val charSequence: CharSequence
        val calendar = Calendar.getInstance()
        var i = calendar.get(Calendar.HOUR_OF_DAY)
        if (i <= 12) {
            charSequence = "上午"
        } else {
            charSequence = "下午"
            i -= 12
        }
        val timeString = getTimeString("$i")
        val timeString2 = getTimeString("${calendar.get(Calendar.MINUTE)}")
        val timeString3 = getTimeString("${calendar.get(Calendar.SECOND)}")
        tv_am.text = charSequence
        tv_time.text = "$timeString:$timeString2"
        tv_second.text = timeString3
    }

    private fun getTimeString(t: String): String {
        return if (t.length <= 1) "0$t" else t
    }


    override fun onDestroy() {
        super.onDestroy()
        this.isTure = false
    }


}