package yc.com.chat.index.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.annotation.JSONField
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_love_text1.*
import yc.com.base.BaseActivity
import yc.com.base.BaseFragment
import yc.com.base.Preference
import yc.com.chat.R
import yc.com.chat.base.constant.SpConstant
import yc.com.chat.base.utils.UserInfoHelper
import yc.com.chat.index.activity.LoveContentActivity
import yc.com.chat.index.activity.SearchContentActivity
import yc.com.chat.index.adapter.IndexLoveAdapter
import yc.com.chat.index.contract.IndexLovceContract
import yc.com.chat.index.model.bean.IndexLoveInfo
import yc.com.chat.index.presenter.IndexLovePresenter
import yc.com.chat.pay.activity.GoPayActivity
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/17 15:49.
 */
class IndexFragment1 : BaseFragment<IndexLovePresenter>(), IndexLovceContract.View {


    override fun getLayoutId(): Int {
        return R.layout.fragment_love_text1
    }


    private var indexLoveAdapter: IndexLoveAdapter? = null
    override fun init() {
        mPresenter = IndexLovePresenter(activity as BaseActivity<*>, this)

        index_recyclerView.layoutManager = LinearLayoutManager(activity)
        indexLoveAdapter = IndexLoveAdapter(null)
        index_recyclerView.adapter = indexLoveAdapter

        RxView.clicks(rl_search).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {

            var isVip = false
            val user = UserInfoHelper.getUser()
            user?.let {
                isVip = (1 == user.has_vip)
            }

            if (isVip) {
                goSearch()
            } else {
                startActivity(Intent(activity, GoPayActivity::class.java))
            }

        }

    }

    private fun goSearch() {
        val obj = et_search.text.trim().toString()
        if (TextUtils.isEmpty(obj)) {
            ToastUtil.toast2(activity, "请输入搜索内容")
        } else {
            startActivity(Intent(activity, SearchContentActivity::class.java).putExtra("keyword", obj))
        }
    }


    override fun showIndexLoveInfos(data: List<IndexLoveInfo>?) {

        indexLoveAdapter?.setNewData(data)
    }



}