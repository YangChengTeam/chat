package yc.com.chat.tools.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_create_qqtx.*
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
 * Created by wanglin  on 2019/6/20 18:09.
 */
class CreateQqtxActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private val cardType = arrayOf("储蓄卡", "信用卡")
    private val choose = arrayOf("中国银行", "农业银行", "建设银行", "招商银行", "民生银行", "交通银行", "华夏银行", "工商银行", "邮政储蓄银行", "浦发银行", "农村信用社")


    override fun getLayoutId(): Int {
        return R.layout.activity_create_qqtx
    }

    override fun init() {
//        tv_title.text = "QQ提现"
        mainToolbar.setBasicInfo(null,"QQ提现",null)
        mainToolbar.init(this)
        initListener()
    }


    fun initListener() {
        RxView.clicks(tv_make).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val trim = tv_bank.text.toString().trim()
            val trim2 = et_num.text.toString().trim()
            val trim3 = tv_pay_time.text.toString().trim()
            val trim4 = et_money.text.toString().trim()
            val trim5 = tv_card_type.text.toString().trim()
            if (TextUtils.isEmpty(trim2) || TextUtils.isEmpty(trim4)) {
                ToastUtil.toast2(this, "请填写尾号或金额")

            } else {
                startActivity(Intent(this, QqtxActivity::class.java)
                        .putExtra(moneyNum, trim4)
                        .putExtra(i_cardType, trim5)
                        .putExtra(i_num, trim2)
                        .putExtra(i_bank, trim).putExtra(i_payTime, trim3))

            }
        }

        RxView.clicks(rl_pay_time).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val datePickDialog = DatePickDialog(this)
            datePickDialog.setYearLimt(18)
            datePickDialog.setTitle("选择时间")
            datePickDialog.setType(DateType.TYPE_YMDHM)
            datePickDialog.setMessageFormat("yyyy-MM-dd HH:mm")
            datePickDialog.setOnChangeLisener(null)
            datePickDialog.setOnSureLisener(C07631())
            datePickDialog.show()
        }

        RxView.clicks(rl_card_type).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val builder2 = AlertDialog.Builder(this)
            builder2.setTitle("卡类型" as CharSequence)
            builder2.setItems(cardType, C07653())
            builder2.show()
        }
        RxView.clicks(rl_pay_bank).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("选择银行卡" as CharSequence)
            builder.setItems(choose, C07642())
            builder.show()
        }
    }

    /* renamed from: com.rjjmc.newproinlove.activity.screenshot.CreateQqtxActivity$1 */
    internal inner class C07631 : OnSureLisener {

        override fun onSure(date: Date) {
            tv_pay_time.text = setDate(date)
        }
    }

    /* renamed from: com.rjjmc.newproinlove.activity.screenshot.CreateQqtxActivity$2 */
    internal inner class C07642 : DialogInterface.OnClickListener {

        override fun onClick(dialog: DialogInterface, which: Int) {
            tv_bank.text = choose[which]
        }
    }

    /* renamed from: com.rjjmc.newproinlove.activity.screenshot.CreateQqtxActivity$3 */
    internal inner class C07653 : DialogInterface.OnClickListener {

        override fun onClick(dialog: DialogInterface, which: Int) {
            tv_card_type.text = cardType[which]
        }
    }

    private fun addZero(d: Int): String {
        return if (d < 10) "0$d" else "" + d
    }


    private fun setDate(date: Date): String {
        val year = date.year - 100 + 2000
        val addZero = addZero(date.month + 1)
        val addZero2 = addZero(date.date)
        val addZero3 = addZero(date.hours)
        return "$year-$addZero-$addZero2 $addZero3:${addZero(date.minutes)}"
    }



}