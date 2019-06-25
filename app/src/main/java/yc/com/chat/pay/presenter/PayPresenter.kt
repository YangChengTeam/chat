package yc.com.chat.pay.presenter

import android.content.Context
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import rx.Subscriber
import yc.com.base.BasePresenter
import yc.com.chat.pay.common.OrderInfo
import yc.com.chat.pay.contract.PayContract
import yc.com.chat.pay.model.bean.GoodInfo
import yc.com.chat.pay.model.bean.OrderGood
import yc.com.chat.pay.model.engine.PayEngine

/**
 *
 * Created by wanglin  on 2019/6/18 18:03.
 */
class PayPresenter(context: Context, view: PayContract.View) : BasePresenter<PayEngine, PayContract.View>(context, view) {

    init {
        mEngine = PayEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {
        if (!isForceUI) return
        getGoodList()
    }

    fun getGoodList() {
        val subscription = mEngine?.getGoodList()?.subscribe(object : Subscriber<ResultInfo<GoodInfo>>() {
            override fun onNext(t: ResultInfo<GoodInfo>?) {
                t?.let {

                    if (t.code == HttpConfig.STATUS_OK && t.data != null)
                        mView?.showGoodInfos(t.data)
                }
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
            }
        })

        mSubscriptions.add(subscription)
    }

    fun createOrder(money: String, title: String, pay_way_name: String, goods_list: List<OrderGood>) {
        mView?.showLoadingDialog("正在创建订单，请稍候...")
        val subscription = mEngine?.createOrder(money, title, pay_way_name, goods_list)?.subscribe(object : Subscriber<ResultInfo<OrderInfo>>() {
            override fun onNext(t: ResultInfo<OrderInfo>?) {
                mView?.dismissDialog()
                t?.let {
                    if (t.code == HttpConfig.STATUS_OK && t.data != null) {

                        val orderInfo = t.data
                        orderInfo.money = java.lang.Float.parseFloat(money)
                        orderInfo.name = title
//                        orderInfo.goodId=goods_id
                        mView?.showOrderInfo(orderInfo)
                    }
                }
            }

            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {
                mView?.dismissDialog()
            }
        })
        mSubscriptions.add(subscription)
    }
}