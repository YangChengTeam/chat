package yc.com.chat.pay.common

import com.alibaba.fastjson.annotation.JSONField

import java.io.Serializable

/**
 * Created by zhangkai on 2017/3/17.
 */

class OrderInfo : Serializable {

    var money: Float? = 0.0f //价格 单位元

    var name: String? = null //会员类型名 也即商品名


    @JSONField(name = "charge_order_sn")
    var order_sn: String? = null //订单号

    var message: String? = null


    var type: String? = null//支付类型

    @JSONField(name = "params")
    var payInfo: PayInfo? = null

    var goodId: String? = null

    constructor() {}


    constructor(money: Float, name: String, order_sn: String) {
        this.money = money
        this.name = name
        this.order_sn = order_sn
    }

    companion object {

        private const val serialVersionUID = -7060210533610464481L
    }
}
