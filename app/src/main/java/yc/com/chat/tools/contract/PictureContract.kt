package yc.com.chat.tools.contract

import yc.com.base.IPresenter
import yc.com.base.IView
import yc.com.chat.tools.model.bean.AutographListBean
import yc.com.chat.tools.model.bean.PhotoListBean

/**
 *
 * Created by wanglin  on 2019/6/19 18:53.
 */
interface PictureContract {

    interface View : IView {
        fun showPictureInfo(t: AutographListBean)
        fun showAllPhotoInfo(t: PhotoListBean)
    }

    interface Presenter : IPresenter {}
}