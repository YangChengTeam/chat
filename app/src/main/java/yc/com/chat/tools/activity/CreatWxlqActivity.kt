package yc.com.chat.tools.activity

import android.content.Intent
import android.text.TextUtils
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_creat_wxlq.*
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
 * Created by wanglin  on 2019/6/20 14:03.
 */
class CreatWxlqActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {



    override fun getLayoutId(): Int {
        return R.layout.activity_creat_wxlq
    }

    override fun init() {
        mainToolbar.setBasicInfo(null,"零钱",null)
        mainToolbar.init(this)

//        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
        RxView.clicks(tv_make).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val trim = et_money.text.toString().trim()
            if (TextUtils.isEmpty(trim)) {
                ToastUtil.toast2(this, "请输入金额")
            } else {
                startActivity(Intent(this, WxlqActivity::class.java).putExtra(next, trim))
            }
        }


    }

}