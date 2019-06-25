package yc.com.chat.pay.common

import android.app.Activity
import android.os.Handler
import android.view.KeyEvent

import java.util.HashMap

/**
 * Created by zhangkai on 2017/3/17.
 */

abstract class IPayImpl(protected var mContext: Activity) {

    var mHandler = Handler()

    abstract fun pay(orderInfo: OrderInfo?, iPayCallback: IPayCallback)

    fun befor(vararg obj: Any): Any? {
        return null
    }

    fun after(vararg obj: Any): Any? {
        return null
    }

    protected operator fun get(cStr: String?, dStr: String): String {
        return if (cStr == null || cStr.isEmpty()) dStr else cStr
    }

    companion object {

        protected val appidStr = "?app_id=2"

        var isGen = false

        var uOrderInfo: OrderInfo? = null
        var uiPayCallback: IPayCallback? = null
        var appid: String? = null


        protected var loadingDialog: LoadingDialog? = null
        private val n = 0
        private val times = 5

        fun checkOrder(orderInfo: OrderInfo, iPayCallback: IPayCallback) {

            if (!isGen) return
            if (loadingDialog == null) return
            if (n == 0) {
                loadingDialog!!.setCancelable(false)
                loadingDialog!!.setOnKeyListener { dialog, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                    }
                    false
                }

                loadingDialog!!.show("正在查询...")
            }
            val url = "http://u.wk990.com/api/index/orders_query$appidStr"
            val params = HashMap<String, String?>()
            params["order_sn"] = orderInfo.order_sn

        }
    }
}
