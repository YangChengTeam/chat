package yc.com.chat.index.presenter

import android.content.Context
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import rx.Subscriber
import yc.com.base.BasePresenter
import yc.com.chat.index.contract.LoveContentContract
import yc.com.chat.index.model.bean.LoveConteInfoWrapper
import yc.com.chat.index.model.bean.LoveContentInfo
import yc.com.chat.index.model.engine.LoveContentEngine

/**
 *
 * Created by wanglin  on 2019/6/17 17:53.
 */
class LoveContentPresenter(context: Context, view: LoveContentContract.View) : BasePresenter<LoveContentEngine, LoveContentContract.View>(context, view), LoveContentContract.Presenter {
    init {

        mEngine = LoveContentEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {

    }

    fun getContentInfos(id: String) {
        val subscription = mEngine?.getContentInfos(id)?.subscribe(object : Subscriber<ArrayList<LoveContentInfo>>() {


            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {

            }

            override fun onNext(t: ArrayList<LoveContentInfo>?) {
                t?.let {
                    mView?.showContentInfos(t)
                }
            }
        })
        mSubscriptions.add(subscription)
    }

    fun searchContent(text: String?) {
        val subscription = mEngine?.searchContent(text)?.subscribe(object : Subscriber<ArrayList<LoveContentInfo>>() {

            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {

            }

            override fun onNext(t: ArrayList<LoveContentInfo>?) {
                t?.let {
                    mView?.showSearchResult(t)
                }
            }

        })
        mSubscriptions.add(subscription)
    }

    fun getLoveContentInfo(category_id: String, page: Int, page_size: Int) {
        val subscription = mEngine?.getLoveContentInfo(category_id, page, page_size)?.subscribe(object : Subscriber<ResultInfo<ArrayList<LoveContentInfo>>>() {
            override fun onNext(t: ResultInfo<ArrayList<LoveContentInfo>>?) {
                t?.let {
                    if (t.code == HttpConfig.STATUS_OK && t.data != null) {
                        mView?.showContentInfos(t.data)
                    }
                }
            }

            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {

            }
        })
        mSubscriptions.add(subscription)
    }

    fun searchLoveContent(keyword: String, page: Int, page_size: Int) {
        val subscription = mEngine?.searchLoveContent(keyword, page, page_size)?.subscribe(object : Subscriber<ResultInfo<LoveConteInfoWrapper>>() {
            override fun onNext(t: ResultInfo<LoveConteInfoWrapper>?) {
                t?.let {
                    if (t.code == HttpConfig.STATUS_OK && t.data != null && t.data.list != null) {
                        mView?.showSearchResult(t.data.list)
                    }
                }
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
            }
        })
        mSubscriptions.add(subscription)
    }

    fun searchLoveContent1(keyword: String, page: Int, page_size: Int) {
        val subscription = mEngine?.searchLoveContent1(keyword, page, page_size)?.subscribe(object : Subscriber<ResultInfo<ArrayList<LoveContentInfo>>>() {
            override fun onNext(t: ResultInfo<ArrayList<LoveContentInfo>>?) {
                t?.let {
                    if (t.code == HttpConfig.STATUS_OK && t.data != null) {
                        mView?.showSearchResult(t.data)
                    }
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