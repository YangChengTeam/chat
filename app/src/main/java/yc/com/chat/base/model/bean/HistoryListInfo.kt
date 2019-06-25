package yc.com.chat.base.model.bean

import com.alibaba.fastjson.annotation.JSONField

/**
 *
 * Created by wanglin  on 2019/6/19 09:06.
 */
class HistoryListInfo {
    var amount: Int = 0
    @JSONField(name = "add_time")
    var createTime: Long? = null
    var description: String? = null
    var finishTime: Any? = null
    @JSONField(name = "order_sn")
    var orderNo: String? = null
    @JSONField(name = "status")
    var orderStatus: Int = 0

    fun getStringStatus(): String {

        return when (orderStatus) {
            0 -> "待支付"

            1 -> "支付中"

            2 -> "取消支付"

            3 -> "支付失败"

            4 -> "支付成功"

            5 -> "发货失败"

            6 -> "交易完成"

            7 -> "申请退货"

            8 -> "退货失败"

            9 -> "退货成功"
            else -> ""
        }

    }
}