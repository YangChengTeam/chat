package yc.com.chat.pay.activity

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.view.View
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.dialog_pay_new.*
import yc.com.base.BaseActivity
import yc.com.base.ObservableManager
import yc.com.chat.R
import yc.com.chat.base.constant.wx_num
import yc.com.chat.base.fragment.DialogFragmentWx
import yc.com.chat.base.fragment.DialogFragmentWx.WxcopyActivityListener
import yc.com.chat.base.utils.UserInfoHelper
import yc.com.chat.constellation.datapicker.DateUtil
import yc.com.chat.pay.common.*
import yc.com.chat.pay.contract.PayContract
import yc.com.chat.pay.model.bean.GoodInfo
import yc.com.chat.pay.model.bean.OrderGood
import yc.com.chat.pay.presenter.PayPresenter
import java.math.BigDecimal
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


/**
 *
 * Created by wanglin  on 2019/6/18 14:18.
 */
class GoPayActivity : BaseActivity<PayPresenter>(), PayContract.View, WxcopyActivityListener {


    private val aliIsVisible = true
    private val fMoney = 199.0f
    private var isPay = false
    //    private var iv_weixin: ImageView? = null

    private var orderno = ""
    private var payway_name = "weixin"


    private var mGoodInfo: GoodInfo? = null

    private val wxIsVisible = true
//    private val wxNum = "allahs888"

    private var aliPayImpl: IAliPay1Impl? = null

    private var wxPayImpl: IWXPay1Impl? = null

    override fun getLayoutId(): Int {
        return R.layout.dialog_pay_new
    }

    override fun init() {
        mPresenter = PayPresenter(this, this)
        aliPayImpl = IAliPay1Impl(this)
        wxPayImpl = IWXPay1Impl(this)

        val userInfo = UserInfoHelper.getUser()



        mainToolbar.setBasicInfo(null, "你需要支付", null)
        mainToolbar.init(this)

        if (!this.wxIsVisible) {
            wxpay_layout.visibility = View.GONE
        }
        if (!this.aliIsVisible) {
            alipay_layout.visibility = View.GONE
        }
        initListener()
    }

    override fun showGoodInfos(data: GoodInfo) {
        mGoodInfo = data
        val originDecimal = BigDecimal(data.price)
        if (data.pay_price > 1) {
            val bigDecimal = BigDecimal(data.pay_price)
            tv_money.text = "¥${bigDecimal.intValueExact()}"

        } else {
            tv_money.text = "¥${data.pay_price}"
        }
        tv_old_money.text = "原价${originDecimal.intValueExact()}"
        tv_person.text = "${(163591 + (getDate() + 163))}"
    }

    private fun getDate(): Long {
        val simpleDateFormat = SimpleDateFormat(DateUtil.ymd, Locale.getDefault())
        val date = Date(System.currentTimeMillis())
        var date2: Date? = null
        try {
            date2 = simpleDateFormat.parse("2018-01-01")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return (date.time - date2!!.time) / 86400000
    }


    private fun getWechatApi() {
        try {
            val intent = Intent("android.intent.action.MAIN")
            val componentName = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
            intent.addCategory("android.intent.category.LAUNCHER")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.component = componentName
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            ToastUtil.toast2(this, "检查到您手机没有安装微信")
        }

    }


    private fun aliPay(orderInfo: OrderInfo?) {
        aliPayImpl?.pay(orderInfo, object : IPayCallback {
            override fun onSuccess(orderInfo: OrderInfo) {
                paySuccess()
            }

            override fun onFailure(orderInfo: OrderInfo) {

            }

        })
    }

    private fun wxPay(orderInfo: OrderInfo?) {
        wxPayImpl?.pay(orderInfo, object : IPayCallback {
            override fun onSuccess(orderInfo: OrderInfo) {
                paySuccess()
            }

            override fun onFailure(orderInfo: OrderInfo) {

            }
        })
    }

    fun paySuccess() {

        val userInfo = UserInfoHelper.getUser()
        userInfo?.has_vip = 1
        UserInfoHelper.saveUser(userInfo)
        ObservableManager.instance.notifyMyObserver(userInfo)
        finish()
    }

    override fun showOrderInfo(data: OrderInfo?) {
        if (payway_name == PayConfig.ali_pay) {
            aliPay(data)
        } else {
            wxPay(data)
        }
    }


//    private fun payWay() {
//        val str = "http://dialogpayment.360lyq.com/api/order/query?orderno=" + this.orderno
//        Log.e("love", "url: $str")
//        (OkHttpUtils.get().url(str) as GetBuilder).build().execute(C07153())
//    }
//
//    private fun saveNewResult(response: String) {
//        if ("1" == (Gson().fromJson(response, PayResultBean::class.java!!) as PayResultBean).getOrderStatus() + "") {
//            this.isPay = true
//            SPUtils.put(this, Constant.isPay, java.lang.Boolean.valueOf(true))
//            LBStat.pay(this.payway_name, this.orderno, true, this.payway_name, this.fMoney, this.tag)
//            ToastUtil.showToast(this, "支付成功!")
//            startActivity(Intent(this, MainActivity::class.java).putExtra(Constant.flag, "goPay"))
//            finish()
//        } else {
//            skipFailure()
//        }
//        this.orderno = ""
//    }
//
//    private fun skipFailure() {
//        val intent = Intent(this, PayResultActivity::class.java)
//        val bundle = Bundle()
//        intent.putExtra("bundle", bundle)
//        bundle.putString(Constant.orderno, this.orderno)
//        bundle.putString(Constant.payMothed, this.payway_name)
//        bundle.putString(Constant.payWay, this.payway_name)
//        bundle.putString(Constant.spSave, Constant.isPay)
//        bundle.putFloat(Constant.fMoney, this.fMoney)
//        startActivity(intent)
//    }

    private fun takeWx() {
        val newInstance = DialogFragmentWx.newInstance("添加老师微信，一键解锁本软件所有功能及搜索功能，进高级学员群！", "老师微信号", wx_num, "添加老师微信进学员群，美女导师不定期指导，提高您的情商！")
        newInstance.setWxCopyActivityListener(this)
        newInstance.show(supportFragmentManager, "wx")
    }


    private fun initListener() {

        RxView.clicks(iv_weixin).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { takeWx() }
        RxView.clicks(alipay_layout).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            payway_name = PayConfig.ali_pay
            getOrder()
        }
        RxView.clicks(wxpay_layout).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            payway_name = PayConfig.wx_pay
            getOrder()
        }
    }

    private fun getOrder() {
        mGoodInfo?.let {
            val goodList = ArrayList<OrderGood>()
            val goodInfo = OrderGood()
            goodInfo.good_id = "${mGoodInfo?.id}"
            goodInfo.num = 1
            goodList.add(goodInfo)
            mPresenter?.createOrder("${mGoodInfo!!.pay_price}", "${mGoodInfo!!.name}", payway_name, goodList)

        }

    }


    override fun wxcopyActivityListener() {
        getWechatApi()
    }

}