package yc.com.chat.tools.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R
import yc.com.chat.tools.model.bean.MottoBean

/**
 *
 * Created by wanglin  on 2019/6/19 14:57.
 */
class MottoAdapter(val list: ArrayList<MottoBean>?) : BaseQuickAdapter<MottoBean, BaseViewHolder>(R.layout.layout_listitem, list) {
    override fun convert(helper: BaseViewHolder?, item: MottoBean?) {
        helper?.let {
            item?.let {
                helper.setText(R.id.list_items, item.title)
            }
        }
    }
}