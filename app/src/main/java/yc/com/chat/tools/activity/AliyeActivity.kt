package yc.com.chat.tools.activity

import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_aliye.*
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
 * Created by wanglin  on 2019/6/20 17:09.
 */
class AliyeActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private var money: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_aliye
    }

    override fun init() {
        intent?.let {
            this.money = intent.getStringExtra(next)
        }

        tv_money.text = SubMoney.subMoney(this.money)

        RxView.clicks(rl_back).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { finish() }
    }


}