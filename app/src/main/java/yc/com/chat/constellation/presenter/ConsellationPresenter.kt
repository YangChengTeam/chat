package yc.com.chat.constellation.presenter

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_constell.*
import rx.Subscriber
import yc.com.base.BasePresenter
import yc.com.chat.constellation.contract.ConsellationContract
import yc.com.chat.constellation.model.bean.ConstellationEngine

/**
 *
 * Created by wanglin  on 2019/6/18 10:38.
 */
class ConsellationPresenter(context: Context, view: ConsellationContract.View) : BasePresenter<ConstellationEngine, ConsellationContract.View>(context, view), ConsellationContract.Presenter {

    init {
        mEngine = ConstellationEngine(context)
    }

    override fun loadData(isForceUI: Boolean, isLoadingUI: Boolean) {

    }


    fun getConstellation(nan: String?, nv: String?) {
        if (TextUtils.isEmpty(nan) || TextUtils.isEmpty(nv)) {
            ToastUtil.toast2(mContext, "请选择星座")
            return
        }
        mView?.showLoadingDialog("")

        val subscription = mEngine?.getConstellation(nan, nv)?.subscribe(object : Subscriber<String>() {
            override fun onNext(t: String?) {
                mView?.dismissDialog()
                t?.let {
                    mView?.showConstellationInfo(t)
                }
            }

            override fun onCompleted() {
            }

            override fun onError(e: Throwable?) {
                mView?.dismissDialog()
            }
        })

        mSubscriptions.add(subscription)
    }
}