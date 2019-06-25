package yc.com.chat.tools.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.widget.GridView
import kotlinx.android.synthetic.main.activity_pic_show_list.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.tools.adapter.MottoAdapter
import yc.com.chat.tools.model.bean.MottoBean


/**
 *
 * Created by wanglin  on 2019/6/19 14:51.
 */
class MottoActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private var data_name: String? = null
    private var mData: ArrayList<MottoBean>? = null
    private val mottoName = arrayOf("爱情", "暗恋", "霸气", "悲伤", "超拽", "分手", "姐妹", "兄弟", "文艺", "经典", "男生", "女生", "情侣", "思恋", "心痛", "英文", "哲理", "座右铭")


    override fun getLayoutId(): Int {
        return R.layout.activity_pic_show_list
    }

    override fun init() {
        mainToolbar.setBasicInfo(null,"个性签名",null)
        mainToolbar.init(this)

        this.mData = ArrayList()
        for (mottoBean in mottoName) {
            mData?.add(MottoBean(mottoBean))
        }
        rv_pic.layoutManager = GridLayoutManager(this, 3)
        val mottoAdapter = MottoAdapter(mData)
        rv_pic.adapter = mottoAdapter

        mottoAdapter.setOnItemClickListener { adapter, view, position ->
            payIntent(position)
        }
    }


    private fun payIntent(position: Int) {
        this.data_name = (mData?.get(position) as MottoBean).title

        val intent = Intent(this, MottoOkActivity::class.java)
        intent.putExtra("data_name", data_name)

        startActivity(intent)
    }

}