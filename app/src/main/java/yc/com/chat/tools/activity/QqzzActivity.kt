package yc.com.chat.tools.activity

import android.view.View
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_qqzz.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.i_pName
import yc.com.chat.base.constant.i_state
import yc.com.chat.base.constant.moneyNum
import yc.com.chat.tools.utils.SubMoney
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 17:58.
 */
class QqzzActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {


    private var money: String? = null
    private var pName: String? = null
    private var state: Int = 0


    override fun getLayoutId(): Int {
        return R.layout.activity_qqzz
    }

    override fun init() {
        intent?.let {
            this.state = intent.getIntExtra(i_state, 0)
            this.money = intent.getStringExtra(moneyNum)
            this.pName = intent.getStringExtra(i_pName)
        }
        initData()
        RxView.clicks(ll_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
    }

    private fun initData() {
        tv_money.text = SubMoney.subMoney(money) + "元"
        if (state == 1) {
            tv_pay_state.text = "转账成功"
            tv_pay_name.text = "已转入" + pName + "的余额"
            tv_qq_ck.visibility = View.INVISIBLE
            tv_qq_tx.visibility = View.INVISIBLE
        }
    }

}