package yc.com.chat.tools.activity

import android.content.Intent
import android.text.TextUtils
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_create_aliye.*
import kotlinx.android.synthetic.main.top_include.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.next
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 17:02.
 */
class CreateAliyeActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_create_aliye
    }

    override fun init() {
//        tv_title.text = "支付宝余额"
        mainToolbar.setBasicInfo(null,"支付宝余额",null)
        mainToolbar.init(this)
        initListener()

    }

    fun initListener() {
//        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }

        RxView.clicks(tv_make).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val trim = et_money.text.toString().trim()
            if (TextUtils.isEmpty(trim)) {
                ToastUtil.toast2(this, "请输入金额")

            } else {
                startActivity(Intent(this, AliyeActivity::class.java).putExtra(next, trim))
            }
        }
    }


}