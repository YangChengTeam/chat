package yc.com.chat.tools.activity

import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.alipay_zhuanzhang.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 11:54.
 */
class AlipayActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {


    override fun getLayoutId(): Int {
        return R.layout.alipay_zhuanzhang
    }


    override fun init() {
        RxView.clicks(tv_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { ToastUtil.toast2(this, "点击收款人更改收款人，点击金额更改金额") }
        RxView.clicks(finish_tv).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
    }

    override fun isStatusBarMateria(): Boolean {
        return false
    }


}