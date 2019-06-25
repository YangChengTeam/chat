package yc.com.chat.index.fragment

import yc.com.base.BaseDialogFragment
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import android.os.Bundle
import android.view.ViewGroup
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.fragment_dialog_look.*
import yc.com.chat.index.utils.UriUtil
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/18 11:10.
 */
class DialogFragmentLook : BaseDialogFragment<BasePresenter<BaseEngine, BaseView>>() {
    private var content: String? = null

    companion object {

        fun newInstance(content: String): DialogFragmentLook {
            val dialogFragmentLook = DialogFragmentLook()
            val bundle = Bundle()
            bundle.putString(UriUtil.LOCAL_CONTENT_SCHEME, content)
            dialogFragmentLook.arguments = bundle
            return dialogFragmentLook
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_dialog_look
    }

    override fun init() {
        arguments?.let {
            content = arguments?.getString(UriUtil.LOCAL_CONTENT_SCHEME)
        }
        tv_content.text = content

        RxView.clicks(rl_close).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { dismiss() }
    }

    override fun getWidth(): Float {
        return 0.8f
    }

    override fun getAnimationId(): Int {
        return R.style.share_anim
    }

    override fun getHeight(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }
}