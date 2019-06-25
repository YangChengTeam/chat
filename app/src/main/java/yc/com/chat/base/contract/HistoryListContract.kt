package yc.com.chat.base.contract

import yc.com.base.IPresenter
import yc.com.base.IView
import yc.com.chat.base.model.bean.HistoryListInfo

/**
 *
 * Created by wanglin  on 2019/6/19 09:19.
 */
interface HistoryListContract {

    interface View : IView {
        fun showHistoryList(data: ArrayList<HistoryListInfo>?)
    }
    interface Presenter : IPresenter {}
}