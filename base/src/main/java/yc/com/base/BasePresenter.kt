package yc.com.base

import android.content.Context
import rx.subscriptions.CompositeSubscription

/**
 * Created by wanglin  on 2018/3/6 10:14.
 */

abstract class BasePresenter<M, V : IView>(protected var mContext: Context?, protected var mView: V?) : IPresenter {

    protected  var mEngine: M? = null


    protected var mSubscriptions: CompositeSubscription = CompositeSubscription()

    private var isFirstLoad = true

    constructor(view: V) : this(null, view) {}

    fun loadData(isForceUI: Boolean) {

        loadData(isFirstLoad or isForceUI, true)

        isFirstLoad = false
    }


    abstract fun loadData(isForceUI: Boolean, isLoadingUI: Boolean)

    override fun subscribe() {
        loadData(false)
    }


    override fun unsubscribe() {
        mSubscriptions.clear()
    }


}
