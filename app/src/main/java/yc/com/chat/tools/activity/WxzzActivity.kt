package yc.com.chat.tools.activity

import android.os.Bundle
import yc.com.chat.R.id.rl_back
import yc.com.chat.R.id.tv_money_time
import android.widget.TextView

import android.widget.RelativeLayout
import yc.com.chat.R.mipmap.wechat_transfer_return
import yc.com.chat.R.mipmap.wechat_transfer_receiving
import yc.com.chat.tools.utils.SubMoney
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_wxzz.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.*
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 14:29.
 */
class WxzzActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {

    private var money: String? = null
    private var moneyTime: String? = null
    private var pName: String? = null
    private var payState: Int = 0
    private var payTime: String? = null

    private var state: Int = 0


    override fun getLayoutId(): Int {
        return R.layout.activity_wxzz
    }

    override fun init() {
        getDate()

//        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
        mainToolbar.setBasicInfo(null, "交易详情", null)
        mainToolbar.init(this)
    }

    private fun getDate() {

        intent?.let {
            this.state = intent.getIntExtra(i_state, 0)
            this.payState = intent.getIntExtra(i_payState, 0)
            this.money = intent.getStringExtra(moneyNum)
            this.pName = intent.getStringExtra(i_pName)
            this.payTime = intent.getStringExtra(i_payTime)
            this.moneyTime = intent.getStringExtra(i_moneyTime)
        }
        initData()
    }

    private fun initData() {
        tv_pay_time.text = "转账时间: $payTime"
        tv_money_time.text = "收钱时间: $moneyTime"
        tv_money.text = "¥${SubMoney.subMoney(money)}"
        if (payState == 1) {
            iv_is_pay.setBackgroundResource(R.mipmap.wechat_transfer_receiving)
            tv_black.visibility = View.VISIBLE
        }
        if (payState == 2) {
            iv_is_pay.setBackgroundResource(R.mipmap.wechat_transfer_return)
        }
        if (state == 0) {
            if (payState == 1) {
                tv_is_pay.text = "待确认收款"
                tv_money_time.visibility = View.GONE
                tv_true_pay.visibility = View.VISIBLE
                tv_black.text = "1天内未确认，将退还给对方。"
                tv_blue.text = "立即退还"
            }
            if (payState == 2) {
                tv_is_pay.text = "已退还"
                tv_blue.visibility = View.GONE
            }
        }
        if (state == 1) {
            if (payState == 0) {
                tv_money.text = "$pName 已收钱"
                tv_black.visibility = View.VISIBLE
                tv_blue.visibility = View.GONE
                tv_black.text = "已存入对方零钱"
            }
            if (payState == 1) {
                tv_is_pay.text = "$pName 确认收款"
                tv_money_time.visibility = View.GONE
                tv_black.text = "1天内朋友未确认，将退还给你。"
                tv_blue.text = "重发转账消息"
            }
            if (payState == 2) {
                tv_is_pay.text = "$pName 已退还"
                tv_black.visibility = View.VISIBLE
                tv_black.text = "已退款到零钱，"
                tv_blue.text = "查看零钱"
            }
        }
    }


}