package yc.com.chat.index.activity

import android.content.Intent
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil

import kotlinx.android.synthetic.main.activity_love_search_content.*
import yc.com.base.BaseActivity
import yc.com.base.ObservableManager

import yc.com.chat.R
import yc.com.chat.base.model.bean.UserInfo
import yc.com.chat.base.utils.UserInfoHelper
import yc.com.chat.index.adapter.TextSearchAdapter
import yc.com.chat.index.contract.LoveContentContract
import yc.com.chat.index.fragment.DialogFragmentLook
import yc.com.chat.index.model.bean.LoveContentInfo
import yc.com.chat.index.presenter.LoveContentPresenter
import yc.com.chat.index.utils.UIUtils
import yc.com.chat.pay.activity.GoPayActivity
import java.util.*
import java.util.concurrent.TimeUnit


/**
 *
 * Created by wanglin  on 2019/6/18 16:15.
 */
class SearchContentActivity : BaseActivity<LoveContentPresenter>(), LoveContentContract.View, Observer {


    private var textSearchAdapter: TextSearchAdapter? = null

    private var keyWord = ""

    private var page = 1
    private var pageSize = 10

    override fun getLayoutId(): Int {
        return R.layout.activity_love_search_content
    }

    override fun init() {

        intent?.let {
            keyWord = intent.getStringExtra("keyword")
            et_search.setText(keyWord)
            et_search.setSelection(keyWord.length)
        }

        mPresenter = LoveContentPresenter(this, this)
//        mainToolbar.setBasicInfo(null, getString(R.string.app_name), null)
        mainToolbar.init(this)
        ObservableManager.instance.addMyObserver(this)
        rv_text.layoutManager = LinearLayoutManager(this)
        textSearchAdapter = TextSearchAdapter(null, keyWord)
        rv_text.adapter = textSearchAdapter
        getData()

        initListener()
    }

    override fun showSearchResult(t: ArrayList<LoveContentInfo>?) {
        if (page == 1)

            textSearchAdapter?.setNewData(t)
        else {
            t?.let {
                textSearchAdapter?.addData(t)
            }
        }

        if (t?.size == pageSize) {
            textSearchAdapter?.loadMoreComplete()
            page++
        } else {
            textSearchAdapter?.loadMoreEnd()
        }

    }


    private fun initListener() {
        textSearchAdapter?.setOnItemChildClickListener { adapter, view, position ->
            val item = textSearchAdapter?.getItem(position)

            var str = item?.chat_name + "\n"

            val details = item?.detail
            details?.let {
                for ((i, value) in details.withIndex()) {
                    str += LoveContentInfo.getSex(value.ans_sex) + ": " + value.content
                    if (i != details.size - 1) {
                        str += "\n"
                    }
                }

            }

            DialogFragmentLook.newInstance(str).show(supportFragmentManager, "look")
        }

        textSearchAdapter?.setOnLoadMoreListener({

            getData()

        }, rv_text)

        RxView.clicks(rl_search).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            var isVip = false
            val user = UserInfoHelper.getUser()
            user?.let {
                isVip = (1 == user.has_vip)
            }

//            val isVip = UserInfoHelper.getUser()?.has_vip
            if (isVip) {
                goSearch()
            } else {
                startActivity(Intent(this, GoPayActivity::class.java))
            }
        }
    }


    override fun update(o: Observable?, arg: Any?) {
        arg?.let {
            when (arg) {
                is UserInfo -> {
                    textSearchAdapter?.notifyDataSetChanged()
                }
                else -> {

                }
            }
        }
    }

    private fun goSearch() {
        this.keyWord = et_search.text.trim().toString()
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtil.toast2(this, "请输入搜索内容")
        } else {
            page = 1
            textSearchAdapter?.setKeyWord(keyWord)
            getData()

//            mPresenter?.searchContent(this.keyWord)
            UIUtils.hideOrShowSoftInput(this, et_search)
        }
    }

    fun getData() {
        mPresenter?.searchLoveContent1(keyWord, page, pageSize)

    }

    override fun onDestroy() {
        super.onDestroy()
        ObservableManager.instance.deleteMyObserver(this)
    }

    override fun showContentInfos(t: ArrayList<LoveContentInfo>) {

    }


}