package yc.com.chat.pay.contract

import yc.com.base.IDialog
import yc.com.base.IPresenter
import yc.com.base.IView
import yc.com.chat.pay.common.OrderInfo
import yc.com.chat.pay.model.bean.GoodInfo

/**
 *
 * Created by wanglin  on 2019/6/18 14:29.
 */
interface PayContract {

    interface View : IView,IDialog {
        fun showGoodInfos(data: GoodInfo)
        fun showOrderInfo(data: OrderInfo?)

    }

    interface Presenter : IPresenter {}
}