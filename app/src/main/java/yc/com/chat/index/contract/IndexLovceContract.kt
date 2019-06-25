package yc.com.chat.index.contract

import yc.com.base.IPresenter
import yc.com.base.IView
import yc.com.chat.index.model.bean.IndexLoveInfo

/**
 *
 * Created by wanglin  on 2019/6/21 14:41.
 */
interface IndexLovceContract {

    interface View : IView {
        fun showIndexLoveInfos(data: List<IndexLoveInfo>?)
    }

    interface Presenter : IPresenter {}
}