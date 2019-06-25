package yc.com.chat.tools.activity

import android.content.Intent
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_create_wxzz.*
import kotlinx.android.synthetic.main.top_include.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.*
import yc.com.chat.constellation.datapicker.DatePickDialog
import yc.com.chat.constellation.datapicker.DateType
import yc.com.chat.constellation.datapicker.OnSureLisener
import java.util.*
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 14:19.
 */
class CreateWxzzActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {

    private var state = 0

    private var type = 0


    override fun getLayoutId(): Int {
        return R.layout.activity_create_wxzz
    }


    override fun init() {
//        tv_title.text = "微信转账"
        mainToolbar.setBasicInfo(null, "微信转账", null)
        mainToolbar.init(this)
        initListener()
    }

    fun initListener() {
//        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
        RxView.clicks(tv_in).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            this.state = 0
            et_name.visibility = View.GONE
        }

        RxView.clicks(tv_out).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            this.state = 1
            et_name.visibility = View.VISIBLE
        }

        RxView.clicks(tv_make).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val trim = et_money.text.toString().trim()
            val trim2 = et_name.text.toString().trim()
            val charSequence = tv_pay_time.text.toString().trim()
            val charSequence2 = tv_money_time.text.toString().trim()
            if (this.state == 0) {
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.toast2(this, "请输入金额")
                } else {
                    startActivity(Intent(this, WxzzActivity::class.java)
                            .putExtra(i_state, this.state)
                            .putExtra(moneyNum, trim)
                            .putExtra(i_payState, this.type)
                            .putExtra(i_payTime, charSequence).putExtra(i_moneyTime, charSequence2))
                }
            } else if (TextUtils.isEmpty(trim) || TextUtils.isEmpty(trim2)) {
                ToastUtil.toast2(this, "请输入金额或昵称")

            } else {
                startActivity(Intent(this, WxzzActivity::class.java)
                        .putExtra(i_state, this.state)
                        .putExtra(moneyNum, trim)
                        .putExtra(i_pName, trim2)
                        .putExtra(i_payState, this.type)
                        .putExtra(i_payTime, charSequence).putExtra(i_moneyTime, charSequence2))

            }
        }
        RxView.clicks(rl_pay_state).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val builder = AlertDialog.Builder(this)
            val charSequenceArr = arrayOf("已收钱", "待收款", "已退款")
            builder.setItems(charSequenceArr) { _, which ->
                tv_pay_state.text = charSequenceArr[which]
                type = which
            }
            builder.show()
        }

        RxView.clicks(rl_pay_time).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val datePickDialog = DatePickDialog(this)
            datePickDialog.setYearLimt(18)
            datePickDialog.setTitle("选择时间")
            datePickDialog.setType(DateType.TYPE_YMDHM)
            datePickDialog.setMessageFormat("yyyy-MM-dd HH:mm")
            datePickDialog.setOnChangeLisener(null)
            datePickDialog.setOnSureLisener(C07692())
            datePickDialog.show()
        }

        RxView.clicks(rl_momey_time).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val datePickDialog2 = DatePickDialog(this)
            datePickDialog2.setYearLimt(18)
            datePickDialog2.setTitle("选择时间")
            datePickDialog2.setType(DateType.TYPE_YMDHM)
            datePickDialog2.setMessageFormat("yyyy-MM-dd HH:mm")
            datePickDialog2.setOnChangeLisener(null)
            datePickDialog2.setOnSureLisener(C07703())
            datePickDialog2.show()
        }

    }


    /* renamed from: com.rjjmc.newproinlove.activity.screenshot.CreateWxzzActivity$2 */
    internal inner class C07692 : OnSureLisener {

        override fun onSure(date: Date) {
            this@CreateWxzzActivity.tv_pay_time!!.text = this@CreateWxzzActivity.setDate(date)
        }
    }

    /* renamed from: com.rjjmc.newproinlove.activity.screenshot.CreateWxzzActivity$3 */
    internal inner class C07703 : OnSureLisener {

        override fun onSure(date: Date) {
            this@CreateWxzzActivity.tv_money_time!!.text = this@CreateWxzzActivity.setDate(date)
        }
    }

    private fun addZero(d: Int): String {
        return if (d < 10) "0$d" else "" + d
    }


    private fun setDate(date: Date): String {
        val year = date.getYear() - 100 + 2000
        val addZero = addZero(date.getMonth() + 1)
        val addZero2 = addZero(date.getDate())
        val addZero3 = addZero(date.getHours())
        return "$year-$addZero-$addZero2 $addZero3:${addZero(date.getMinutes())}"
    }


}