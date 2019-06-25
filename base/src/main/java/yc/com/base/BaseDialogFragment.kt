package yc.com.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.kk.utils.ScreenUtil
import com.umeng.analytics.MobclickAgent

/**
 * Created by wanglin  on 2018/3/6 11:14.
 */

abstract class BaseDialogFragment<P : BasePresenter<*, *>> : DialogFragment(), IView {

    protected var mPresenter: P? = null
    private var rootView: View? = null

    protected abstract fun getWidth(): Float

    abstract fun getAnimationId(): Int

    abstract fun getHeight(): Int


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val window = dialog.window

        if (rootView == null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            rootView = inflater.inflate(getLayoutId(), container, false)

            //            window.setLayout((int) (RxDeviceTool.getScreenWidth(getActivity()) * getWidth()), getHeight());//这2行,和上面的一样,注意顺序就行;
            window.setWindowAnimations(getAnimationId())
        }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)


        return rootView

    }

    override fun onStart() {
        super.onStart()
        val window = dialog.window
        if (window != null) {
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))//注意此处

            val layoutParams = window.attributes

            layoutParams.width = (ScreenUtil.getWidth(activity) * getWidth()).toInt()
            layoutParams.height = getHeight()
            window.attributes = layoutParams
        }
        init()
    }


    override fun onResume() {
        super.onResume()
        MobclickAgent.onPageStart(this.javaClass.simpleName)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (EmptyUtils.isNotEmpty(mPresenter))
            mPresenter?.subscribe()
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPageEnd(this.javaClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EmptyUtils.isNotEmpty(mPresenter))
            mPresenter?.unsubscribe()

        Runtime.getRuntime().gc()
    }
}
