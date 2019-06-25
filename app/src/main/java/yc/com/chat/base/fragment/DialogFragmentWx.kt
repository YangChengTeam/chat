package yc.com.chat.base.fragment

import android.content.Context
import android.os.Bundle
import android.text.ClipboardManager
import android.view.ViewGroup
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.dialog_weixin.*
import yc.com.base.BaseDialogFragment
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.index.utils.UriUtil
import java.util.concurrent.TimeUnit

class DialogFragmentWx : BaseDialogFragment<BasePresenter<BaseEngine, BaseView>>() {
    private var info: String? = null


    private var wxContent: String? = null
    private var wxInfo: String? = null
    private var wxNum: String? = null

    override fun getWidth(): Float {
        return 0.8f
    }

    override fun getAnimationId(): Int {
        return R.style.share_anim
    }

    override fun getHeight(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_weixin
    }

    override fun init() {


        this.info = arguments?.getString("info")
        this.wxInfo = arguments?.getString("wxInfo")
        this.wxNum = arguments?.getString("num")
        this.wxContent = arguments?.getString(UriUtil.LOCAL_CONTENT_SCHEME)


        tv_info.text = this.info
        tv_weixin_info.text = this.wxInfo
        tv_weixin_num.text = this.wxNum
        tv_weixin_content.text = this.wxContent
        initListener()
    }

    private fun initListener() {
        RxView.clicks(rl_close).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { dismiss() }
        RxView.clicks(ll_skip).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            onClickCopy(wxNum)
            mwxcopyActivityListener?.wxcopyActivityListener()
            dismiss()
        }
    }

    interface WxcopyActivityListener {
        fun wxcopyActivityListener()
    }

    private var mwxcopyActivityListener: WxcopyActivityListener? = null

    fun onClickCopy(s: String?) {
        (context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).text = s
        ToastUtil.toast2(context, "已复制微信号")
    }


    fun setWxCopyActivityListener(ifinishListener: WxcopyActivityListener) {
        this.mwxcopyActivityListener = ifinishListener
    }

    companion object {

        fun newInstance(info: String, wxInfo: String, num: String, content: String): DialogFragmentWx {
            val dialogFragmentWx = DialogFragmentWx()
            val bundle = Bundle()
            bundle.putString("info", info)
            bundle.putString("wxInfo", wxInfo)
            bundle.putString("num", num)
            bundle.putString(UriUtil.LOCAL_CONTENT_SCHEME, content)
            dialogFragmentWx.arguments = bundle
            return dialogFragmentWx
        }
    }
}