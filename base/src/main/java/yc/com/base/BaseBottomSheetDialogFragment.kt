package yc.com.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umeng.analytics.MobclickAgent
import primary.answer.yc.com.base.R


/**
 * Created by wanglin  on 2018/3/22 08:47.
 */

abstract class BaseBottomSheetDialogFragment<P : BasePresenter<*, *>> : BottomSheetDialogFragment(), IView {

    protected var mPresenter: P? = null
    protected lateinit var mContext: BaseActivity<*>
    private var dialog: BottomSheetDialog? = null
    protected var rootView: View? = null
    private var mBehavior: BottomSheetBehavior<View>? = null
    protected var loadingView: BaseLoadingView? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mContext = context as BaseActivity<*>
    }

    override fun onStart() {
        super.onStart()
        val window = getDialog().window
        val windowParams = window.attributes
        //这里设置透明度
        windowParams.dimAmount = 0.5f
        //        windowParams.width = (int) (ScreenUtil.getWidth(mContext) * 0.98);
        window.attributes = windowParams
        window.setWindowAnimations(R.style.share_anim)

        init()
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        dialog = BottomSheetDialog(mContext, theme)
        if (rootView == null) {
            //缓存下来的 View 当为空时才需要初始化 并缓存
            rootView = LayoutInflater.from(mContext).inflate(getLayoutId(), null)
            loadingView = BaseLoadingView(mContext)
//            ButterKnife.bind(this, rootView!!)
        }
        dialog?.setContentView(rootView)

        mBehavior = BottomSheetBehavior.from(rootView?.parent as View)
        (rootView?.parent as View).setBackgroundColor(Color.TRANSPARENT)
        rootView?.post {
            /**
             * PeekHeight 默认高度 256dp 会在该高度上悬浮
             * 设置等于 view 的高 就不会卡住
             */
            /**
             * PeekHeight 默认高度 256dp 会在该高度上悬浮
             * 设置等于 view 的高 就不会卡住
             */
            mBehavior?.peekHeight = rootView!!.height
        }
        return dialog as BottomSheetDialog
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

        //解除缓存 View 和当前 ViewGroup 的关联
        (rootView?.parent as ViewGroup).removeView(rootView)
        Runtime.getRuntime().gc()
    }

}
