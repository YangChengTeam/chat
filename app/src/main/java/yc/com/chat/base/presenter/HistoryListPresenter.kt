package yc.com.chat.base.presenter

import android.content.Context
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import rx.Subscriber
import yc.com.base.BasePresenter
import yc.com.chat.base.contract.HistoryListContract
import yc.com.chat.base.model.bean.HistoryListInfo
import yc.com.chat.base.model.engine.HistoryListEngine

/**
 *
 * Created by wanglin  on 2019/6/19 09:19.
 */
class HistoryListPresenter(context: Context, view: HistoryListContract.View) : BasePresenter<HistoryListEngine, HistoryListContract.View>(context, view), HistoryListContract.Presenter {

    init {
        mEngine = HistoryListEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {
        if (!isForceUI) return
        getHistoryList()
    }

    private fun getHistoryList() {

        val subscription = mEngine?.getHistoryList()?.subscribe(object : Subscriber<ResultInfo<ArrayList<HistoryListInfo>>>() {
            override fun onNext(t: ResultInfo<ArrayList<HistoryListInfo>>?) {
                t?.let {
                    if (t.code==HttpConfig.STATUS_OK&& t.data!=null){
                        mView?.showHistoryList(t.data)
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