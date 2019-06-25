package yc.com.chat.combat.presenter

import android.content.Context
import com.alibaba.fastjson.JSON
import rx.Subscriber
import yc.com.base.BasePresenter
import yc.com.base.Preference
import yc.com.chat.base.constant.SpConstant
import yc.com.chat.combat.contract.PicLoveContract
import yc.com.chat.combat.model.bean.PicContentBean
import yc.com.chat.combat.model.engine.ShortLoveEngine

/**
 *
 * Created by wanglin  on 2019/6/17 19:03.
 */
class PicLovePresenter(context: Context, view: PicLoveContract.View) : BasePresenter<ShortLoveEngine, PicLoveContract.View>(context, view), PicLoveContract.Presenter {


    init {
        mEngine = ShortLoveEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {

    }

    fun getShortLoveInfos(joketype: String) {


        val subscription = mEngine?.getShortLoveInfos(joketype)?.subscribe(object : Subscriber<ArrayList<PicContentBean>>() {
            override fun onNext(t: ArrayList<PicContentBean>?) {
                t?.let {
                    mView?.showShortLoveContents(t)

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