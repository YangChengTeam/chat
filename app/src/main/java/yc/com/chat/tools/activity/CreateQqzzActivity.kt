package yc.com.chat.tools.activity

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_create_qqzz.*
import kotlinx.android.synthetic.main.top_include.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.i_pName
import yc.com.chat.base.constant.i_state
import yc.com.chat.base.constant.moneyNum
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 17:52.
 */
class CreateQqzzActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {

    private var state = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_create_qqzz
    }


    override fun init() {
//        tv_title.text = "QQ转账"
        mainToolbar.setBasicInfo(null,"QQ转账",null)
        mainToolbar.init(this)
        initListener()
    }

    fun initListener() {
//        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }

        RxView.clicks(tv_in).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            state = 0
            et_name.visibility = View.GONE
        }
        RxView.clicks(tv_out).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            state = 1
            et_name.visibility = View.VISIBLE
        }

        RxView.clicks(tv_make).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val trim = et_money.text.toString().trim()
            val trim2 = et_name.text.toString().trim()
            if (this.state == 0) {
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.toast2(this, "请输入金额")

                } else {
                    startActivity(Intent(this, QqzzActivity::class.java)
                            .putExtra(i_state, this.state).putExtra(moneyNum, trim))

                }
            } else if (TextUtils.isEmpty(trim) || TextUtils.isEmpty(trim2)) {
                ToastUtil.toast2(this, "请输入金额或昵称")

            } else {
                startActivity(Intent(this, QqzzActivity::class.java).putExtra(i_state, this.state).putExtra(moneyNum, trim).putExtra(i_pName, trim2))
            }
        }
    }




}