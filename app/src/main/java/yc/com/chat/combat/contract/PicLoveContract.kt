package yc.com.chat.combat.contract

import yc.com.base.IPresenter
import yc.com.base.IView
import yc.com.chat.combat.model.bean.PicContentBean

/**
 *
 * Created by wanglin  on 2019/6/17 19:04.
 */
interface PicLoveContract {
    interface View : IView {
        fun showShortLoveContents(t: List<PicContentBean>)
    }

    interface Presenter : IPresenter {}
}