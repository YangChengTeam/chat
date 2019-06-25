package yc.com.chat.tools.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_show_pic.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.i_position
import yc.com.chat.tools.adapter.PicShowAdapter


/**
 *
 * Created by wanglin  on 2019/6/21 11:32.
 */
class ShowPicActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {

    private val names = arrayOf("宠物", "高端", "风景(旅游)", "开场", "美酒", "美食", "预选", "红酒晚会")
    private val pics = intArrayOf(R.mipmap.ps_cw, R.mipmap.ps_dyk, R.mipmap.ps_fj, R.mipmap.ps_kc, R.mipmap.ps_mj, R.mipmap.ps_ms, R.mipmap.ps_yx, R.mipmap.ps_hjwh)


    private var picAdapter: PicShowAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_show_pic
    }


    override fun init() {
        mainToolbar.setBasicInfo(null, "展示面", null)
        mainToolbar.init(this)
        picAdapter = PicShowAdapter(pics, names.toList() as ArrayList<String>)
        rv_show.layoutManager = GridLayoutManager(this, 3)
        rv_show.adapter = picAdapter
        initListener()
    }

    fun initListener() {
        picAdapter?.setOnItemClickListener { adapter, view, position ->
            startActivity(Intent(this, ShowPictListActivity::class.java).putExtra(i_position, position))
        }
    }

}