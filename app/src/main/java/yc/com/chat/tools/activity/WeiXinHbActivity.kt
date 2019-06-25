package yc.com.chat.tools.activity

import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_weixinhb.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 11:59.
 */
class WeiXinHbActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_weixinhb
    }


    override fun init() {
        RxView.clicks(finish_img).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }

        RxView.clicks(hb_Img).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { ToastUtil.toast2(this, "点击零钱金额或银行卡尾号进行更改") }
    }


}