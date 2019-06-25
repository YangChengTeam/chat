package yc.com.chat.tools.activity

import android.os.Bundle
import android.widget.Toast
import yc.com.chat.R.id.set2_btn
import yc.com.chat.R.id.set_btn
import yc.com.chat.R.id.lq_back_img
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_weixinlq.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 11:44.
 */
class WeiXinLqActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
//    private var lq_back_img: ImageView? = null
//    private var set2_btn: Button? = null
//    private var set_btn: Button? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_weixinlq
    }

    override fun init() {

        initListener()
    }


    fun initListener() {
        RxView.clicks(lq_back_img).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
        RxView.clicks(set_btn).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { ToastUtil.toast2(this, "点击金额随意更改金额") }
        RxView.clicks(set2_btn).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { ToastUtil.toast2(this, "点击金额随意更改金额") }
    }

    override fun isStatusBarMateria(): Boolean {
        return false
    }


}