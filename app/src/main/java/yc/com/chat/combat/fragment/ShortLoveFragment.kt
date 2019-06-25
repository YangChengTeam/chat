package yc.com.chat.combat.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON

import kotlinx.android.synthetic.main.fragment_short.*
import yc.com.base.BaseActivity
import yc.com.base.BaseFragment
import yc.com.base.ObservableManager
import yc.com.base.Preference
import yc.com.chat.R
import yc.com.chat.base.constant.SpConstant
import yc.com.chat.base.model.bean.UserInfo
import yc.com.chat.base.utils.UserInfoHelper
import yc.com.chat.combat.activity.LongPicActivity
import yc.com.chat.combat.adapter.PicContentAdapter
import yc.com.chat.combat.contract.PicLoveContract
import yc.com.chat.combat.model.bean.PicContentBean
import yc.com.chat.combat.presenter.PicLovePresenter
import yc.com.chat.pay.activity.GoPayActivity
import java.util.*


/**
 *
 * Created by wanglin  on 2019/6/17 18:42.
 */
class ShortLoveFragment : BaseFragment<PicLovePresenter>(), PicLoveContract.View, Observer {

    private val loveType = "5"

    private var shortLoveInfo by Preference(SpConstant.SHORT_LOVE_INFO + loveType, "")
    private var flag = ""
    private var picContentAdapter: PicContentAdapter? = null

    private var shortLoveInfos: List<PicContentBean>? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_short
    }

    override fun init() {

        mPresenter = PicLovePresenter(activity as BaseActivity<*>, this)

        ObservableManager.instance.addMyObserver(this)

        mPresenter?.getShortLoveInfos(loveType)

        shortLoveInfo.let {
            shortLoveInfos = JSON.parseArray(shortLoveInfo, PicContentBean::class.java)
        }

        lv_pic.layoutManager = LinearLayoutManager(activity)
        picContentAdapter = PicContentAdapter(shortLoveInfos)
        lv_pic.adapter = picContentAdapter
        initListener()
    }


    private fun initListener() {
        picContentAdapter?.setOnItemClickListener { adapter, view, position ->

            var isVip = false
            val user = UserInfoHelper.getUser()
            user?.let {
                isVip = (1 == user.has_vip)
            }
            if (position < 5 || isVip) {
                flag = ""
                val item = picContentAdapter?.getItem(position)
                LongPicActivity.startActivity(activity as BaseActivity<*>, item?.joke_name, item?.joke_content)
            } else {
                this@ShortLoveFragment.startActivity(Intent(activity, GoPayActivity::class.java))
            }
        }


        picContentAdapter?.setOnLoadMoreListener({}, lv_pic)



    }


    override fun showShortLoveContents(t: List<PicContentBean>) {
        shortLoveInfo = JSON.toJSONString(t)
        picContentAdapter?.setNewData(t)
        picContentAdapter?.loadMoreEnd()
    }


    override fun update(o: Observable?, arg: Any?) {
        arg?.let {
            when (arg) {
                is UserInfo -> {
                    picContentAdapter?.notifyDataSetChanged()
                }
                else -> {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ObservableManager.instance.deleteMyObserver(this)
    }

}