package yc.com.chat.pay.model.engine

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.engin.HttpCoreEngin
import rx.Observable
import yc.com.base.BaseEngine
import yc.com.chat.base.constant.good_list_url
import yc.com.chat.base.constant.order_init_url
import yc.com.chat.pay.model.bean.GoodInfo
import yc.com.chat.pay.model.bean.OrderGood
import yc.com.chat.pay.common.OrderInfo

/**
 * Created by wanglin  on 2019/6/18 14:30.
 */
class PayEngine(context: Context) : BaseEngine(context) {

    fun getGoodList(): Observable<ResultInfo<GoodInfo>> {

        return HttpCoreEngin.get(mContext).rxpost(good_list_url, object : TypeReference<ResultInfo<GoodInfo>>() {}.type, null, true, true, true) as Observable<ResultInfo<GoodInfo>>
    }

    fun createOrder(money: String, title: String, pay_way_name: String, goods_list: List<OrderGood>): Observable<ResultInfo<OrderInfo>> {

        return HttpCoreEngin.get(mContext).rxpost(order_init_url, object : TypeReference<ResultInfo<OrderInfo>>() {}.type, mutableMapOf(
                "money" to money,
                "title" to title,
                "pay_way_name" to pay_way_name,
                "goods_list" to JSON.toJSONString(goods_list)

        ), true, true, true) as Observable<ResultInfo<OrderInfo>>
    }
}
