package yc.com.chat.tools.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R
import yc.com.chat.tools.model.bean.CatalogBean.ResultContentBean


/**
 *
 * Created by wanglin  on 2019/6/19 10:42.
 */
class AutographAdapter(titles: List<ResultContentBean>?, private val names: Array<String>, private val picList: IntArray) : BaseQuickAdapter<ResultContentBean, BaseViewHolder>(R.layout.fragment_autograph_item, titles) {


    override fun convert(helper: BaseViewHolder?, item: ResultContentBean?) {
        helper?.let {
            item?.let {
                val position = helper.adapterPosition
                helper.setText(R.id.tv_name, item.name)
                        .setText(R.id.tv_contribute, names[position])
                        .setBackgroundRes(R.id.iv_pic, picList[position])
            }

        }
    }

}