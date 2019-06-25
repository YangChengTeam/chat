package yc.com.chat.tools.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_autograph_list.*
import yc.com.base.BaseActivity
import yc.com.chat.R
import yc.com.chat.base.constant.i_id
import yc.com.chat.base.constant.name
import yc.com.chat.tools.adapter.AutographListAdapter
import yc.com.chat.tools.contract.AutographContract
import yc.com.chat.tools.model.bean.AutographListBean
import yc.com.chat.tools.model.bean.PhotoBean
import yc.com.chat.tools.presenter.AutographPresenter


/**
 *
 * Created by wanglin  on 2019/6/19 10:59.
 */
class AutographListActivity : BaseActivity<AutographPresenter>(), AutographContract.View {


    private var mId = 0

    private var autographListAdapter: AutographListAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_autograph_list
    }

    override fun init() {
        intent?.let {
            val name = intent.getStringExtra(name)
            mId = intent.getIntExtra(i_id, 0)
            mainToolbar.setBasicInfo(null, name, null)
            mainToolbar.init(this)
        }
        mPresenter = AutographPresenter(this, this)
        mPresenter?.getAutographInfo("$mId")

        autographListAdapter = AutographListAdapter(null,"autograph")
        list_recyclerView.layoutManager = GridLayoutManager(this, 3)
        list_recyclerView.adapter = autographListAdapter
        initListener()

    }


    private fun initListener() {
        autographListAdapter?.setOnItemClickListener { adapter, view, position ->
            val resultContentBean = autographListAdapter?.getItem(position)
            val intent = Intent(this@AutographListActivity, SmallPictureActivity::class.java)
            intent.putExtra(i_id, resultContentBean?.cid)
            intent.putExtra(name, resultContentBean?.cname)
            startActivity(intent)
        }
    }

    override fun showtAutographInfo(t: AutographListBean) {
        autographListAdapter?.setNewData(t.result_content)
    }

    override fun showPhotoInfo(t: PhotoBean) {

    }
}