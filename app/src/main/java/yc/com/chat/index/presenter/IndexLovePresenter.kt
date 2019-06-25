package yc.com.chat.index.presenter

import android.content.Context
import com.alibaba.fastjson.JSON
import com.kk.securityhttp.domain.ResultInfo
import com.kk.securityhttp.net.contains.HttpConfig
import rx.Subscriber
import yc.com.base.BasePresenter
import yc.com.base.Preference
import yc.com.chat.base.constant.SpConstant
import yc.com.chat.index.contract.IndexLovceContract
import yc.com.chat.index.model.bean.IndexLoveInfo
import yc.com.chat.index.model.engine.IndexLoveEngine

/**
 *
 * Created by wanglin  on 2019/6/21 14:40.
 */
class IndexLovePresenter(context: Context, view: IndexLovceContract.View) : BasePresenter<IndexLoveEngine, IndexLovceContract.View>(context, view), IndexLovceContract.Presenter {

    private var indexInfo by Preference(SpConstant.INDEX_INFO, "")

    init {
        mEngine = IndexLoveEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {
        if (!isForceUI) return
        getIndexLoveInfos()
    }

    fun getIndexLoveInfos() {
        indexInfo.let {
            val indexInfos = JSON.parseArray(indexInfo, IndexLoveInfo::class.java)
            indexInfos?.let {
                createNewData(indexInfos)
            }
        }

        val subscription = mEngine?.getIndexLoveInfos()?.subscribe(object : Subscriber<ResultInfo<ArrayList<IndexLoveInfo>>>() {
            override fun onNext(t: ResultInfo<ArrayList<IndexLoveInfo>>?) {
                t?.let {
                    if (t.code == HttpConfig.STATUS_OK && t.data != null) {

                        indexInfo = JSON.toJSONString(t.data)
                        createNewData(t.data)
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

    fun createNewData(datas: List<IndexLoveInfo>?) {

        datas?.let {
            for ((i, value) in datas.withIndex()) {
                when (i) {
                    0 -> value.name = "撩妹恋爱话术"
                    1 -> value.name = "撩妹恋爱惯例"
                    2 -> value.name = "短期关系--速约术话"
                    3 -> value.name = "长期关系--连环惯例"
                    4 -> value.name = "柔性恋爱疗法"
                    5 -> value.name = "撩妹极限聊天术"
                }
            }

            mView?.showIndexLoveInfos(datas)
        }
    }


}