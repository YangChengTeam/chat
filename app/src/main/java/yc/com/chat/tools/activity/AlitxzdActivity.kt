package yc.com.chat.tools.activity

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_alitxzd.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R

import yc.com.chat.base.constant.*
import yc.com.chat.tools.utils.SubMoney
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 17:26.
 */
class AlitxzdActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private val bankImg = intArrayOf(R.mipmap.bank_boc, R.mipmap.bank_abc, R.mipmap.bank_ccb, R.mipmap.bank_cmb, R.mipmap.bank_cmbc, R.mipmap.bank_comm, R.mipmap.bank_hxbank, R.mipmap.bank_icbc, R.mipmap.bank_psbc, R.mipmap.bank_spdb, R.mipmap.bank_ydrcb)
    private var bankPosition: Int = 0
    private val choose = arrayOf("中国银行", "中国农业银行", "中国建设银行", "招商银行", "中国民生银行", "交通银行", "华夏银行", "中国工商银行", "中国邮政储蓄银行", "浦发银行", "农村信用社")
    private var isExit: Boolean = false

    private var money: String? = null
    private var name: String? = null
    private var num: String? = null
    private var payTime: String? = null
    private var payTimeIn: String? = null
    private var payTypeIn: String? = null

    private var serviceCharge: String? = null


    override fun getLayoutId(): Int {

        return R.layout.activity_alitxzd
    }

    override fun init() {
        intent?.let {
            val stringExtra = intent.getStringExtra(moneyNum)
            this.payTime = intent.getStringExtra(i_payTime)
            this.payTimeIn = intent.getStringExtra(i_payTimeIn)
            this.bankPosition = intent.getIntExtra(i_bank, 0)
            this.num = intent.getStringExtra(i_num)
            this.serviceCharge = intent.getStringExtra(i_payService)
            this.name = intent.getStringExtra(i_pName)
            this.payTypeIn = intent.getStringExtra(i_payTypeIn)
            this.isExit = intent.getBooleanExtra(i_isExit, true)
            this.money = SubMoney.subMoney(stringExtra)
            this.serviceCharge = SubMoney.subMoney(this.serviceCharge)
        }
        initData()

        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
    }


    private fun initData() {

        val substring = payTime?.substring(5)
        tv_time.text = substring
        tv_time1.text = substring


        tv_time2.text = this.payTime

        tv_timeIn.text = this.payTimeIn?.substring(5)


        iv_bank.setBackgroundResource(bankImg[bankPosition])
        tv_bank.text = choose[bankPosition]
        tv_bank_name.text = choose[bankPosition] + "（" + this.num + "）" + this.name
        tv_money.text = this.money
        if (this.isExit) {
            tv_pay_service.text = serviceCharge
        } else {
            view_service.visibility = View.GONE
            rl_pay_service.visibility = View.GONE
        }
        tv_type.text = payTypeIn
        val substring2 = payTime?.substring(0, 4)
        val substring3 = payTime?.substring(5, 7)
        tv_order_num.text = substring2 + substring3 + payTime?.substring(8, 10) + "0004001" + System.currentTimeMillis()
        tv_order.text = ((Math.random() * 9000.0).toInt() + 1000).toString() + ""
    }

    override fun isStatusBarMateria(): Boolean {
        return false
    }
}