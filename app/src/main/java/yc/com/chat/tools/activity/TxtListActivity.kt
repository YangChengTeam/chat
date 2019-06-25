package yc.com.chat.tools.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_txt_list.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.i_id
import yc.com.chat.tools.adapter.TxtListAdapter


/**
 *
 * Created by wanglin  on 2019/6/21 10:00.
 */
class TxtListActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {

    private val names = arrayOf("1. 谈话的基本要素", "2. 十字纵横话术思维", "3. 女人喜欢引导式聊天", "4. 话术脉络思维【极限话术】", "5. 框架", "6. 话题", "7. 语言的即视效应", "8. 破解废物测试", "9. 话题的铺垫", "10. 幽默与调侃", "11. 完美邀约")

    private var textListAdapter: TxtListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_txt_list
    }

    override fun init() {
//        mainToolbar.setBasicInfo(null, getString(R.string.app_name), null)
        mainToolbar.init(this)

        lv_list.layoutManager = LinearLayoutManager(this)
        textListAdapter = TxtListAdapter(this.names.toList() as ArrayList<String>)
        lv_list.adapter = textListAdapter

        initListener()
    }

    private fun initListener() {
        textListAdapter?.setOnItemClickListener { adapter, view, position ->
            this@TxtListActivity.startActivity(Intent(this@TxtListActivity, ReadTxtActivity::class.java).putExtra(i_id, position))
        }
    }

}