package yc.com.chat.tools.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_autograph_list.*
import yc.com.base.BaseActivity
import yc.com.chat.R
import yc.com.chat.base.constant.i_id
import yc.com.chat.base.constant.name
import yc.com.chat.tools.adapter.AutographListAdapter
import yc.com.chat.tools.contract.PictureContract
import yc.com.chat.tools.model.bean.AutographListBean
import yc.com.chat.tools.model.bean.PhotoListBean
import yc.com.chat.tools.presenter.PicturePresenter


/**
 *
 * Created by wanglin  on 2019/6/19 18:39.
 */
class PictureListActivity : BaseActivity<PicturePresenter>(), PictureContract.View {



    private var pid: Int = 0

    private var autographListAdapter: AutographListAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_autograph_list
    }

    override fun init() {

        intent?.let {
            val name = intent.getStringExtra(name)
            pid = intent.getIntExtra(i_id, 0)
            mainToolbar.setBasicInfo(null, name, null)
            mainToolbar.init(this)
        }

        mPresenter = PicturePresenter(this, this)

        autographListAdapter = AutographListAdapter(null, "picture")
        list_recyclerView.adapter = autographListAdapter
        list_recyclerView.layoutManager = GridLayoutManager(this, 3)

        mPresenter?.getPictureInfo("${pid}")
        initListener()
    }


    override fun showPictureInfo(t: AutographListBean) {
        autographListAdapter?.setNewData(t.result_content)
    }

    fun initListener() {
        autographListAdapter?.setOnItemClickListener { adapter, view, position ->
            val resultContentBean = autographListAdapter?.getItem(position)
            val intent = Intent(this, PictureList2Activity::class.java)

            intent.putExtra(i_id, resultContentBean?.cid)
            intent.putExtra(name, resultContentBean?.cname)
            startActivity(intent)
        }
    }

    override fun showAllPhotoInfo(t: PhotoListBean) {

    }
}