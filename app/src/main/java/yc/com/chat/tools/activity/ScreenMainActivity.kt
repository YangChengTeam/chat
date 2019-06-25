package yc.com.chat.tools.activity

import android.content.Intent
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_main_screen.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/20 13:59.
 */
class ScreenMainActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {


    override fun getLayoutId(): Int {
        return R.layout.activity_main_screen
    }


    override fun init() {

        mainToolbar.setBasicInfo(null, "微商截图王", null)
        initListener()
    }


    fun initListener() {
        RxView.clicks(ll_wx_lq).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { startActivity(Intent(this, CreatWxlqActivity::class.java)) }
        RxView.clicks(ll_wx_zz).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { startActivity(Intent(this, CreateWxzzActivity::class.java)) }
        RxView.clicks(ll_wx_qb).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { startActivity(Intent(this, CreateWxwdqbActivity::class.java)) }


        RxView.clicks(ll_wx_tx).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { startActivity(Intent(this, CreateWxtxActivity::class.java)) }
        RxView.clicks(ll_ali_ye).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { startActivity(Intent(this, CreateAliyeActivity::class.java)) }

        RxView.clicks(ll_ali_txzb).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { startActivity(Intent(this, CreateAlitxzdActivity::class.java)) }

        RxView.clicks(ll_qq_zz).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { startActivity(Intent(this, CreateQqzzActivity::class.java)) }

        RxView.clicks(ll_qq_tx).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { startActivity(Intent(this, CreateQqtxActivity::class.java)) }

        RxView.clicks(ll_wx).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { startActivity(Intent(this, CreateWxChatActivity::class.java)) }

        RxView.clicks(ll_ali).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { }

        RxView.clicks(ll_qq).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe { }
    }

}