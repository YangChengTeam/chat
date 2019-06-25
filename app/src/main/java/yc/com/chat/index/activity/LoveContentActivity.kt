package yc.com.chat.index.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.jakewharton.rxbinding.view.RxView
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_love_content.*
import yc.com.base.BaseActivity
import yc.com.chat.R
import yc.com.chat.base.utils.UserInfoHelper
import yc.com.chat.index.adapter.NewTextContentAdapter
import yc.com.chat.index.contract.LoveContentContract
import yc.com.chat.index.fragment.DialogFragmentLook
import yc.com.chat.index.model.bean.LoveContentInfo
import yc.com.chat.index.presenter.LoveContentPresenter
import yc.com.chat.index.utils.UIUtils
import yc.com.chat.pay.activity.GoPayActivity
import java.util.concurrent.TimeUnit

/**
 *
 * Created by wanglin  on 2019/6/17 17:08.
 */
class LoveContentActivity : BaseActivity<LoveContentPresenter>(), LoveContentContract.View {


    private var id: String = ""
    private var newTextContentAdapter: NewTextContentAdapter? = null

    private var page = 1
    private val pagesize = 10
//    private var isPay = false

    companion object {
        fun startActivity(context: Context, id: String?) {
            val intent = Intent(context, LoveContentActivity::class.java)
            intent.putExtra("i_id", id)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_love_content
    }

    override fun init() {
        if (intent != null) {
            id = intent.getStringExtra("i_id")
        }

//        mainToolbar.setBasicInfo(null, getString(R.string.app_name), null)
        mainToolbar.init(this)

        mPresenter = LoveContentPresenter(this, this)
//        mPresenter?.getContentInfos(id)
        mPresenter?.getLoveContentInfo(id, page, pagesize)

        rv_text.layoutManager = LinearLayoutManager(this)
        newTextContentAdapter = NewTextContentAdapter(null)
        rv_text.adapter = newTextContentAdapter

        initListener()
    }

    override fun showContentInfos(t: ArrayList<LoveContentInfo>) {

        if (page == 1)
            newTextContentAdapter?.setNewData(t)
        else {
            newTextContentAdapter?.addData(t)
        }
        if (t.size == pagesize) {
            newTextContentAdapter?.loadMoreComplete()
            page++
        } else {
            newTextContentAdapter?.loadMoreEnd()
        }

    }

    private fun initListener() {
        newTextContentAdapter?.setOnItemChildClickListener { adapter, view, position ->
            val item = newTextContentAdapter?.getItem(position)
            onClick(item, position)
        }
//        newTextContentAdapter?.setOnItemClickListener { adapter, view, position ->
//            val item = newTextContentAdapter?.getItem(position)
//            onClick(item, position)
//        }

        newTextContentAdapter?.setOnLoadMoreListener({
            mPresenter?.getLoveContentInfo(id, page, pagesize)
        }, rv_text)

        RxView.clicks(rl_search).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {

            var isVip = false
            val user = UserInfoHelper.getUser()
            user?.let {
                isVip = (1 == user.has_vip)
            }

            if (isVip) {
                goSearch()
            } else {
                startActivity(Intent(this, GoPayActivity::class.java))
            }
        }


    }

    fun onClick(item: LoveContentInfo?, position: Int) {

        item?.let {

            var str = item.chat_name + "\n"

            val details = item.details
            details?.let {
                for ((i, value) in details.withIndex()) {
                    str += LoveContentInfo.getSex(value.ans_sex) + ": " + value.content
                    if (i != details.size - 1) {
                        str += "\n"
                    }
                }
            }

//            val str = "${Html.fromHtml(joke_name)}\n${Html.fromHtml(joke_content)}"

            var isVip = false
            val user = UserInfoHelper.getUser()
            user?.let {
                isVip = (1 == user.has_vip)
            }
            if (isVip || position < 5) {
                DialogFragmentLook.newInstance(str).show(supportFragmentManager, "look")
            } else {
                startActivity(Intent(this, GoPayActivity::class.java))
            }
        }


    }

    private fun goSearch() {
        val keyWord = et_search.text.trim().toString()
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtil.toast2(this, "请输入搜索内容")
        } else {
//            mPresenter?.searchContent(keyWord)
            page = 1
            mPresenter?.searchLoveContent1(keyWord, page, pagesize)

            UIUtils.hideOrShowSoftInput(this, et_search)
        }
    }

    override fun showSearchResult(t: ArrayList<LoveContentInfo>?) {
        newTextContentAdapter?.isSearch(true)
        if (page == 1)
            newTextContentAdapter?.setNewData(t)
        else
            t?.let { newTextContentAdapter?.addData(t) }

        if (t?.size == pagesize) {
            newTextContentAdapter?.loadMoreComplete()
            page++
        } else {
            newTextContentAdapter?.loadMoreEnd()
        }
    }

}