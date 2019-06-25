package yc.com.chat.tools.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R


/**
 *
 * Created by wanglin  on 2019/6/19 15:32.
 */
class ToolsMoreAdapter(private val pics: IntArray, names: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.gridview_more_item, names) {


    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.let {
            item?.let {
                val position = helper.adapterPosition
                helper.setText(R.id.tv_more_name, item)
                        .setBackgroundRes(R.id.iv_more_pic, pics[position])
            }
        }

    }

}