package yc.com.chat.tools.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R


/**
 *
 * Created by wanglin  on 2019/6/21 11:35.
 */
class PicShowAdapter(private val pics: IntArray, names: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_show_pic_item, names) {


    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.let {
            item?.let {
                val pos = helper.adapterPosition
                helper.setImageResource(R.id.iv_show_pic, pics[pos])
                helper.setText(R.id.tv_show_text, item)
            }
        }
    }


}