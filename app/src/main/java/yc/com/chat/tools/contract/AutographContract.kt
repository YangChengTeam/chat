package yc.com.chat.tools.contract

import yc.com.base.IPresenter
import yc.com.base.IView
import yc.com.chat.tools.model.bean.AutographListBean
import yc.com.chat.tools.model.bean.PhotoBean

/**
 *
 * Created by wanglin  on 2019/6/19 11:25.
 */
interface AutographContract {

    interface View : IView {
        fun showtAutographInfo(t: AutographListBean)
        fun showPhotoInfo(t: PhotoBean)
    }

    interface Presenter : IPresenter {}
}