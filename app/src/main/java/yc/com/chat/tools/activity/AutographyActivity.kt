package yc.com.chat.tools.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.fastjson.JSON
import kotlinx.android.synthetic.main.activity_autograph_list.*
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
 * Created by wanglin  on 2019/6/19 10:37.
 */
class AutographyActivity : BaseActivity<BasePresenter<BaseEngine, BaseView>>() {
    private var mCatalogList: List<CatalogBean.ResultContentBean>? = null

    private var names: Array<String>? = null
    private val picList = intArrayOf(R.mipmap.pic1, R.mipmap.pic2, R.mipmap.pic3, R.mipmap.pic4, R.mipmap.pic5, R.mipmap.pic6, R.mipmap.pic7, R.mipmap.pic8, R.mipmap.pic9, R.mipmap.pic10)

    private var autographAdapter: AutographAdapter? = null

    override fun getLayoutId(): Int {

        return R.layout.activity_autograph_list
    }


    override fun init() {
        mainToolbar.setBasicInfo(null, "头像大全", null)
        mainToolbar.init(this)
//        mainToolbar.getCenterView()?.findViewById<TextView>(R.i_id.tv_title)?.setTextColor(ContextCompat.getColor(this, R.color.black))

        ll_type.visibility = View.GONE
        getData()
        initData()
        initListener()

    }


    private fun initListener() {
        autographAdapter?.setOnItemClickListener { adapter, view, position ->

            val item = autographAdapter?.getItem(position)
            val intent = Intent(this, AutographListActivity::class.java)

            intent.putExtra(i_id, item?.id)
            intent.putExtra(name, item?.name)
            startActivity(intent)
        }

    }


    private fun getData() {
        val str = TextToString.texttostring("catalog.txt", this)

        this.mCatalogList = JSON.parseObject(str, CatalogBean::class.java).result_content

//        this.mCatalogList = (Gson().fromJson(TextToString.texttostring("catalog.txt", this), CatalogBean::class.java) as CatalogBean).getResult_content()
    }

    private fun initData() {
        this.names = resources.getStringArray(R.array.head_name)
        autographAdapter = AutographAdapter(this.mCatalogList, names!!, this.picList)
        list_recyclerView.layoutManager = LinearLayoutManager(this)
        list_recyclerView.adapter = autographAdapter

    }
}