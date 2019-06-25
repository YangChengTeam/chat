package yc.com.chat.tools.activity

import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_qqtx.*
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
 * Created by wanglin  on 2019/6/20 18:13.
 */
class QqtxActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private var bank: String? = null
    private var cardType: String? = null

    private var money: String? = null
    private var num: String? = null
    private var payTime: String? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_qqtx
    }

    override fun init() {
        intent?.let {
            val stringExtra = intent.getStringExtra(moneyNum)
            this.payTime = intent.getStringExtra(i_payTime)
            this.bank = intent.getStringExtra(i_bank)
            this.num = intent.getStringExtra(i_num)
            this.cardType = intent.getStringExtra(i_cardType)
            this.money = SubMoney.subMoney(stringExtra)
        }

        initData()
        RxView.clicks(ll_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
    }


    private fun initData() {
        tv_time.text = payTime + ":" + (Random().nextInt(50) + 10)
        tv_bank.text = "$bank$cardType($num)"
        tv_money.text = money + "元"
        tv_money1.text = money + "元"
        tv_order.text = "4430118083" + System.currentTimeMillis()
    }

}