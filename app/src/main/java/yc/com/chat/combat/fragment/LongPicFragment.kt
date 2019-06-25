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
 * Created by wanglin  on 2019/6/18 09:00.
 */
class LongPicFragment : BaseFragment<PicLovePresenter>(), PicLoveContract.View, Observer {

    private val loveType = "2"

    private var shortLoveInfo by Preference(SpConstant.SHORT_LOVE_INFO + loveType, "")
    private var picContentAdapter: PicContentAdapter? = null
    private var flag = ""
    private var shortLoveInfos: List<PicContentBean>? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_short
    }

    override fun init() {

        ObservableManager.instance.addMyObserver(this)
        mPresenter = PicLovePresenter(activity as BaseActivity<*>, this)
        mPresenter?.getShortLoveInfos("2")

        shortLoveInfo.let {
            shortLoveInfos = JSON.parseArray(shortLoveInfo, PicContentBean::class.java)
        }

        lv_pic.layoutManager = LinearLayoutManager(activity)
        picContentAdapter = PicContentAdapter(shortLoveInfos)
        lv_pic.adapter = picContentAdapter

        initListener()
    }


    override fun showShortLoveContents(t: List<PicContentBean>) {
        shortLoveInfo = JSON.toJSONString(t)
        picContentAdapter?.setNewData(t)
        picContentAdapter?.loadMoreEnd()
    }


    private fun initListener() {
        picContentAdapter?.setOnLoadMoreListener({}, lv_pic)

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
                this@LongPicFragment.startActivity(Intent(this@LongPicFragment.activity, GoPayActivity::class.java))
            }
        }
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

//    fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View {
//        val inflate = inflater.inflate(C0707R.layout.fragment_short, container, false)
//        this.isPay = (SPUtils.get(getActivity(), Constant.isPay, java.lang.Boolean.valueOf(false)) as Boolean).booleanValue()
//        this.isFree = SPUtils.get(getActivity(), Constant.isFree, "0")
//        initView(inflate)
//        initData()
//        return inflate
//    }

    override fun onResume() {
        super.onResume()
//        this.isPay = (SPUtils.get(getActivity(), Constant.isPay, java.lang.Boolean.valueOf(false)) as Boolean).booleanValue()
//        this.flag = getActivity().getIntent().getStringExtra(Constant.flag)
//        if (this.isPay && this.adapter != null) {
//            this.adapter = PicContentAdapter(getActivity(), this.list, this.isPay)
//            this.listView!!.setAdapter(this.adapter)
//        }
    }
}