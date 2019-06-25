package yc.com.chat.tools.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_pic_show_list.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.i_id
import yc.com.chat.base.constant.i_position
import yc.com.chat.tools.adapter.PicShowListAdapter


/**
 *
 * Created by wanglin  on 2019/6/21 11:47.
 */
class ShowPictListActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {

    private val names = arrayOf("宠物", "高端", "风景(旅游)", "开场", "美酒", "美食", "预选", "红酒晚会")
    private val num = intArrayOf(145, 26, 80, 14, 53, 255, 17, 4)
    private var position = 0

    private val urls = arrayOf("cw", "gd", "fj", "kc", "mj", "ms", "yx", "hj")

    private var picListAdapter: PicShowListAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_pic_show_list
    }


    override fun init() {
        intent?.let {
            this.position = intent.getIntExtra(i_position, 0)

            mainToolbar.setBasicInfo(null, names[position], null)
            mainToolbar.init(this)
        }
        picListAdapter = PicShowListAdapter(this, num[position], urls[position])
        rv_pic.layoutManager = GridLayoutManager(this, 3)
        rv_pic.adapter = picListAdapter

        initListener()
    }


    fun initListener() {

        picListAdapter?.setOnItemClickListener (object :PicShowListAdapter.OnItemClickListener{
            override fun onItemClick(adapter1: PicShowListAdapter, view: View, position: Int) {
                startActivity(Intent(this@ShowPictListActivity, ShowPicPageActivity::class.java)
                        .putExtra(i_position, this@ShowPictListActivity.position).putExtra(i_id, position))
            }
        })
    }


}