package yc.com.chat.tools.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R


/**
 *
 * Created by wanglin  on 2019/6/21 10:53.
 */
class TxtListAdapter(lists: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_txt_list_item, lists) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.let {
            item?.let {
                helper.setText(R.id.tv_list_name, item)
            }
        }

    }


}