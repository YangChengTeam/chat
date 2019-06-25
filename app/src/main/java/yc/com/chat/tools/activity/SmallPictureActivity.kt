package yc.com.chat.tools.activity

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_picture_list2.*
import yc.com.base.BaseActivity
import yc.com.chat.R
import yc.com.chat.base.constant.*
import yc.com.chat.tools.adapter.SmallPictureAdapter
import yc.com.chat.tools.contract.AutographContract
import yc.com.chat.tools.model.bean.AutographListBean
import yc.com.chat.tools.model.bean.PhotoBean
import yc.com.chat.tools.presenter.AutographPresenter


/**
 *
 * Created by wanglin  on 2019/6/19 11:46.
 */
class SmallPictureActivity : BaseActivity<AutographPresenter>(), AutographContract.View {


    private val bigPhoto = ArrayList<String?>()
    private var smallPictureAdapter: SmallPictureAdapter? = null
    private var mId = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_picture_list2
    }

    override fun init() {

        intent?.let {
            val name = intent.getStringExtra(name)
            mId = intent.getIntExtra(i_id, 0)
            mainToolbar.setBasicInfo(null, name, null)
            mainToolbar.init(this)
        }

        mPresenter = AutographPresenter(this, this)


        smallPictureAdapter = SmallPictureAdapter(null)
        small_recyclerView.layoutManager = GridLayoutManager(this, 3)
        small_recyclerView.adapter = smallPictureAdapter
        mPresenter?.getPhotoInfo("${mId}")
        initListener()
    }

    private fun initListener() {
        smallPictureAdapter?.setOnItemClickListener { adapter, view, position ->
            val item = smallPictureAdapter?.getItem(position)
            val intent = Intent(this, PicturePageActivity::class.java)

            intent.putExtra(i_position, position)
            intent.putExtra(i_flag, "small")
            intent.putExtra(i_list, bigPhoto)
            startActivity(intent)
        }
    }


    private fun initBigPicture(list: List<PhotoBean.ResultContentBean>?) {
        list?.let {
            for ((i, value) in list.withIndex()) {
                this.bigPhoto.add(value.bigPhoto)
            }
        }

    }

    override fun showPhotoInfo(t: PhotoBean) {
        initBigPicture(t.result_content)
        smallPictureAdapter?.setNewData(t.result_content)
    }


    override fun showtAutographInfo(t: AutographListBean) {

    }

}