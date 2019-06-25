package yc.com.chat.index.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.base.BaseActivity
import yc.com.chat.R
import yc.com.chat.index.activity.LoveContentActivity
import yc.com.chat.index.model.bean.IndexLoveInfo
import yc.com.chat.index.utils.ItemDecorationHelper

/**
 *
 * Created by wanglin  on 2019/6/21 14:46.
 */
class IndexLoveAdapter(items: List<IndexLoveInfo>?) : BaseQuickAdapter<IndexLoveInfo, BaseViewHolder>(R.layout.index_love_item_view, items) {

    override fun convert(helper: BaseViewHolder?, item: IndexLoveInfo?) {
        helper?.let {
            item?.let {
                helper.setText(R.id.tv_title, item.name)
                val indexItemRecyclerView = helper.getView<RecyclerView>(R.id.index_item_recyclerView)

                indexItemRecyclerView.layoutManager = GridLayoutManager(mContext, 3)
                val indexLoveItemAdapter = IndexLoveItemAdapter(item.children)
                indexItemRecyclerView.adapter = indexLoveItemAdapter

//                val decorationHelper = ItemDecorationHelper(mContext, 10, 10)
//                indexItemRecyclerView.removeItemDecoration(decorationHelper)
//                indexItemRecyclerView.addItemDecoration(decorationHelper)
                indexLoveItemAdapter.setOnItemClickListener { adapter, view, position ->
                    val childrenBean = indexLoveItemAdapter.getItem(position)

                    LoveContentActivity.startActivity(mContext, "${childrenBean?.id}")
                }
            }
        }
    }
}