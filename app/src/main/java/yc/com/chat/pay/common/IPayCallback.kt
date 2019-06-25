package yc.com.chat.pay.common

/**
 * Created by zhangkai on 2017/3/17.
 */

interface IPayCallback {
    fun onSuccess(orderInfo: OrderInfo)

    fun onFailure(orderInfo: OrderInfo)
}
