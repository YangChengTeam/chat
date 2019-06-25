package yc.com.chat.tools.presenter

import android.content.Context
import rx.Subscriber
import yc.com.base.BasePresenter
import yc.com.chat.tools.contract.AutographContract
import yc.com.chat.tools.model.bean.AutographListBean
import yc.com.chat.tools.model.bean.PhotoBean
import yc.com.chat.tools.model.engine.AutographEngine

/**
 *
 * Created by wanglin  on 2019/6/19 11:26.
 */
class AutographPresenter(context: Context, view: AutographContract.View) : BasePresenter<AutographEngine, AutographContract.View>(context, view), AutographContract.Presenter {

    init {
        mEngine = AutographEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {

    }

    fun getAutographInfo(fid: String) {
        val subscription = mEngine?.getAutographInfo(fid)?.subscribe(object : Subscriber<AutographListBean>() {
            override fun onNext(t: AutographListBean?) {
                t?.let {
                    mView?.showtAutographInfo(t)
                }
            }

            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {

            }
        })
        mSubscriptions.add(subscription)
    }


    fun getPhotoInfo(cid: String) {
        val subscription = mEngine?.getPhotoInfo(cid)?.subscribe(object : Subscriber<PhotoBean>() {
            override fun onNext(t: PhotoBean?) {
                t?.let {
                    mView?.showPhotoInfo(t)
                }
            }

            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {

            }
        })
        mSubscriptions.add(subscription)
    }
}