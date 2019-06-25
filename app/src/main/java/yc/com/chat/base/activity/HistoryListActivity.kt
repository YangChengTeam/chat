package yc.com.chat.base.activity

import android.content.ClipboardManager
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kk.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_history_list.*
import yc.com.base.BaseActivity
import yc.com.chat.R
import yc.com.chat.base.adapter.HistoryListAdapter
import yc.com.chat.base.contract.HistoryListContract
import yc.com.chat.base.model.bean.HistoryListInfo
import yc.com.chat.base.presenter.HistoryListPresenter


/**
 *
 * Created by wanglin  on 2019/6/19 09:04.
 */
class HistoryListActivity : BaseActivity<HistoryListPresenter>(), HistoryListContract.View {


    private var historyListAdapter: HistoryListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_history_list
    }


    override fun init() {

        mainToolbar.setBasicInfo(null, "历史订单", null)
        mainToolbar.init(this)

        mPresenter = HistoryListPresenter(this, this)

        rv_history_list.layoutManager = LinearLayoutManager(this)
        historyListAdapter = HistoryListAdapter(null)
        rv_history_list.adapter = historyListAdapter
        initListener()
    }


    private fun initListener() {
        historyListAdapter?.setOnItemClickListener { adapter, view, position ->
            val historyListInfo = historyListAdapter?.getItem(position)
            ToastUtil.toast2(this@HistoryListActivity, historyListInfo?.getStringStatus())
        }

        historyListAdapter?.onItemLongClickListener = object : BaseQuickAdapter.OnItemLongClickListener {
            override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int): Boolean {
                val item = historyListAdapter?.getItem(position)
                onClickCopy(item?.orderNo)
                return true
            }
        }

        historyListAdapter?.setOnLoadMoreListener({}, rv_history_list)
    }

    override fun showHistoryList(data: ArrayList<HistoryListInfo>?) {
        historyListAdapter?.setNewData(data)
        historyListAdapter?.loadMoreEnd()
    }

    fun onClickCopy(order: String?) {
        (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).text = order
        ToastUtil.toast2(this, "已复制订单号!")
    }


}