package yc.com.chat.index.contract

import yc.com.base.IPresenter
import yc.com.base.IView
import yc.com.chat.index.model.bean.LoveContentInfo

/**
 *
 * Created by wanglin  on 2019/6/17 17:53.
 */
interface LoveContentContract {
    interface View : IView {
        fun showContentInfos(t: ArrayList<LoveContentInfo>)
        fun showSearchResult(t: ArrayList<LoveContentInfo>?)
    }

    interface Presenter : IPresenter {}
}