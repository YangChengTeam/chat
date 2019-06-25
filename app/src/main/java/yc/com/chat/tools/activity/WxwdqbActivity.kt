package yc.com.chat.tools.activity

import kotlinx.android.synthetic.main.activity_wxwdqb.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.next
import yc.com.chat.tools.utils.SubMoney


/**
 *
 * Created by wanglin  on 2019/6/20 16:16.
 */
class WxwdqbActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private var money: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_wxwdqb
    }

    override fun init() {

        mainToolbar.setBasicInfo(null, "我的钱包", null)
        mainToolbar.init(this)
        intent?.let {
            this.money = intent.getStringExtra(next)
        }

        tv_money.text = "¥${SubMoney.subMoney(money)}"

//        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
    }

}