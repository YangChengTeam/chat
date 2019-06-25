package yc.com.chat.tools.activity

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.jakewharton.rxbinding.view.RxView
import kotlinx.android.synthetic.main.activity_read_txt.*

import kotlinx.android.synthetic.main.top_main_include.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.i_id
import yc.com.chat.tools.utils.LcndUtil
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/21 11:02.
 */
class ReadTxtActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    /* renamed from: id */
    private var id = 1
    private var nId: Int = 0
    private val names = arrayOf("1. 谈话的基本要素", "2. 十字纵横话术思维", "3. 女人喜欢引导式聊天", "4. 话术脉络思维【极限话术】", "5. 框架", "6. 话题", "7. 语言的即视效应", "8. 破解废物测试", "9. 话题的铺垫", "10. 幽默与调侃", "11. 完美邀约")


    override fun getLayoutId(): Int {
        return R.layout.activity_read_txt
    }

    override fun init() {
        intent?.let {
            id = intent.getIntExtra(i_id, 1) + 1
        }
        nId = this.id
//        mainToolbar.setBasicInfo(null, getString(R.string.app_name), null)

        initData(nId)
        updateView()
        initListener()

    }


    fun initListener() {
        RxView.clicks(tv_last).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            nId--
            initData(nId)
        }
        RxView.clicks(tv_next).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            nId++
            initData(nId)
        }


    }

    private fun initData(i: Int) {
        tv_txt.text = LcndUtil.readAssetsTxt(this, "loveTxt$i")
//        tv_top_title.text = names[i - 1]
        mainToolbar.setBasicInfo(null, names[i - 1], null)
//        mainToolbar.init(this)
        updateView()
        scrollView.scrollTo(0, 0)
    }

    private fun updateView() {
        if (this.nId == 1) {
            tv_last.visibility = View.GONE
        } else {
            tv_last.visibility = View.VISIBLE
        }
        if (this.nId == 11) {
            tv_next.visibility = View.GONE
        } else {
            tv_next.visibility = View.VISIBLE
        }
    }

}