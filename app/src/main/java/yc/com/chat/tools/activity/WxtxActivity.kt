package yc.com.chat.tools.activity

import android.view.View
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_wxtx.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.*
import yc.com.chat.tools.utils.SubMoney
import java.util.*
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 16:42.
 */
class WxtxActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private var bank: String? = null
    private var isExit: Boolean = false
    private var money: String? = null
    private var num: String? = null
    private var payTime: String? = null

    private var serviceCharge: String? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_wxtx
    }


    override fun init() {

        mainToolbar.setBasicInfo(null, "提现详情", null)
        intent?.let {
            val stringExtra = intent.getStringExtra(moneyNum)
            this.payTime = intent.getStringExtra(i_payTime)
            this.bank = intent.getStringExtra(i_bank)
            this.num = intent.getStringExtra(i_num)
            this.serviceCharge = intent.getStringExtra(i_payService)
            this.isExit = intent.getBooleanExtra(i_isExit, true)
            this.money = SubMoney.subMoney(stringExtra)
        }

        initData()

        RxView.clicks(tv_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
    }


    private fun initData() {
        tv_time.text = "$payTime:${(Random().nextInt(50) + 10)}"
        tv_bank.text = "$bank 尾号$num"
        tv_money.text = this.money
        if (this.isExit) {
            tv_pay_service.text = this.serviceCharge
        } else {
            rl_pay_service.visibility = View.GONE
        }
    }

}