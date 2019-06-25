package yc.com.chat.tools.presenter

import android.content.Context
import rx.Subscriber
import yc.com.base.BasePresenter
import yc.com.chat.tools.contract.PictureContract
import yc.com.chat.tools.model.bean.AutographListBean
import yc.com.chat.tools.model.bean.PhotoListBean
import yc.com.chat.tools.model.engine.PictureEngine

/**
 *
 * Created by wanglin  on 2019/6/19 18:53.
 */
class PicturePresenter(context: Context, view: PictureContract.View) : BasePresenter<PictureEngine, PictureContract.View>(context, view), PictureContract.Presenter {
    init {

        mEngine = PictureEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {

    }

    fun getPictureInfo(fid: String?) {
        val subscription = mEngine?.getPictureInfo(fid)?.subscribe(object : Subscriber<AutographListBean>() {
            override fun onNext(t: AutographListBean?) {
                t?.let {
                    mView?.showPictureInfo(t)
                }
            }

            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {

            }

        })

        mSubscriptions.add(subscription)
    }

    fun getAllPhotoInfo(cid: String) {
        val subscription = mEngine?.getAllPhotoInfo(cid)?.subscribe(object : Subscriber<PhotoListBean>() {
            override fun onNext(t: PhotoListBean?) {
                t?.let {
                    mView?.showAllPhotoInfo(t)
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