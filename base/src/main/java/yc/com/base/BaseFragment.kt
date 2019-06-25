package yc.com.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umeng.analytics.MobclickAgent

/**
 * Created by wanglin  on 2018/3/6 10:52.
 */

abstract class BaseFragment<P : BasePresenter<*, *>> : Fragment(), IView {


    protected var rootView: View? = null
    protected var mPresenter: P? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        if (rootView == null) {
            rootView = LayoutInflater.from(activity).inflate(getLayoutId(), container, false)
        }


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (EmptyUtils.isNotEmpty(mPresenter)) {
            mPresenter?.subscribe()
        }
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onPageStart(this.javaClass.simpleName)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPageEnd(this.javaClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (EmptyUtils.isNotEmpty(mPresenter)) {
            mPresenter?.unsubscribe()
        }
        Runtime.getRuntime().gc()
    }

}
