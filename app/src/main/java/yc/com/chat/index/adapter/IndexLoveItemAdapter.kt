package yc.com.chat.index.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R
import yc.com.chat.index.model.bean.IndexLoveInfo

/**
 *
 * Created by wanglin  on 2019/6/21 14:54.
 */
class IndexLoveItemAdapter(items: ArrayList<IndexLoveInfo.ChildrenBean>?) : BaseQuickAdapter<IndexLoveInfo.ChildrenBean, BaseViewHolder>(R.layout.index_love_sub_item, items) {


    override fun convert(helper: BaseViewHolder?, item: IndexLoveInfo.ChildrenBean?) {
        helper?.let {
            item?.let {
                helper.setText(R.id.name1, item.name)
            }
        }
    }
}