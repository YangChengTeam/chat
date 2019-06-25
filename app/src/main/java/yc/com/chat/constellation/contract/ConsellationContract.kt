package yc.com.chat.constellation.contract

import yc.com.base.IDialog
import yc.com.base.IPresenter
import yc.com.base.IView

/**
 *
 * Created by wanglin  on 2019/6/18 10:38.
 */
interface ConsellationContract {

    interface View : IView,IDialog {
        fun showConstellationInfo(t: String)
    }

    interface Presenter : IPresenter {}
}