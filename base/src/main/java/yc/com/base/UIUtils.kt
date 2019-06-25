package yc.com.base


import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import java.lang.RuntimeException

import java.util.regex.Pattern

/**
 * Created by wanglin  on 2018/2/5 17:37.
 */

@SuppressLint("StaticFieldLeak")
object UIUtils {


    private var context: Context? = null

    fun init(context: Context) {
        this.context = context
    }

    fun getContext(): Context {
        if (context == null) throw RuntimeException("context必须先初始化")
        return context!!.applicationContext
    }

    private val handler = Handler(Looper.getMainLooper())

    fun post(runnable: Runnable) {
        handler.post(runnable)
    }

    fun postDelay(runnable: Runnable, delay: Long) {
        handler.postDelayed(runnable, delay)
    }

    /**
     * 判断是否是常用11位数手机号
     * @param phoneNumber
     * @return
     */
    fun isPhoneNumber(phoneNumber: String): Boolean {
        val p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$")
        val m = p.matcher(phoneNumber)
        return m.matches()
    }

    /**
     * 是否是6或者4位数字验证码
     * @param phoneNumber
     * @param type 6 或 4
     * @return
     */
    fun isNumberCode(phoneNumber: String, type: Int): Boolean {
        var rex = "^\\d{4}$"
        if (type == 6) {
            rex = "^\\d{6}$"
        }
        val p = Pattern.compile(rex)
        val m = p.matcher(phoneNumber)
        return m.matches()
    }

}
