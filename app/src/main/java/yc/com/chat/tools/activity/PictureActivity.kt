package yc.com.chat.tools.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.fastjson.JSON
import kotlinx.android.synthetic.main.activity_pic_show_list.*
import yc.com.base.BaseActivity
import yc.com.base.BaseEngine
import yc.com.base.BasePresenter
import yc.com.base.BaseView
import yc.com.chat.R
import yc.com.chat.base.constant.i_id
import yc.com.chat.base.constant.name
import yc.com.chat.tools.adapter.AutographAdapter
import yc.com.chat.tools.model.bean.CatalogBean
import yc.com.chat.tools.utils.TextToString


/**
 *
 * Created by wanglin  on 2019/6/19 18:15.
 */
class PictureActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private var mCatalogList: List<CatalogBean.ResultContentBean>? = null


    private val picList = intArrayOf(R.mipmap.photo1, R.mipmap.photo2, R.mipmap.photo3, R.mipmap.photo4, R.mipmap.photo5, R.mipmap.photo6, R.mipmap.photo7)

    private var autographAdapter: AutographAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_pic_show_list
    }


    override fun init() {

        mainToolbar.setBasicInfo(null, "头签大全", null)
        mainToolbar.init(this)

        val s = TextToString.texttostring("picture.txt", this)
        mCatalogList = JSON.parseObject(s, CatalogBean::class.java).result_content

        val names = resources.getStringArray(R.array.pic_name)
        rv_pic.layoutManager = LinearLayoutManager(this)
        autographAdapter = AutographAdapter(mCatalogList, names, picList)
        rv_pic.adapter = autographAdapter

        initListener()

    }

    fun initListener() {
        autographAdapter?.setOnItemClickListener { adapter, view, position ->

            val item = autographAdapter?.getItem(position)
            val intent = Intent(this, PictureListActivity::class.java)

            intent.putExtra(i_id, item?.id)
            intent.putExtra(name, item?.name)
            this@PictureActivity.startActivity(intent)
        }
    }


}