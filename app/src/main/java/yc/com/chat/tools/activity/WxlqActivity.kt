package yc.com.chat.tools.activity

import android.widget.TextView
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_wxlq.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.next
import yc.com.chat.tools.utils.SubMoney
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 14:11.
 */
class WxlqActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private var money: String? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_wxlq
    }

    override fun init() {
        mainToolbar.setBasicInfo(null, "零钱", null)
        mainToolbar.init(this)
        mainToolbar.getRightView()?.findViewById<TextView>(R.id.tv_right_title)?.text = "零钱明细"

        intent?.let {
            this.money = intent.getStringExtra(next)
        }
        tv_money.text = "¥${SubMoney.subMoney(this.money)}"
//        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
    }

}