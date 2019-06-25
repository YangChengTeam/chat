package yc.com.chat.index.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R
import yc.com.chat.index.model.bean.LoveContentInfo
import yc.com.chat.index.widget.MyTextView


/**
 *
 * Created by wanglin  on 2019/6/18 16:45.
 */
class TextSearchAdapter(list: List<LoveContentInfo>?, private var mKeyWord: String) : BaseQuickAdapter<LoveContentInfo, BaseViewHolder>(R.layout.listview_text_search_item, list) {

    fun setKeyWord(keyWord: String){
        mKeyWord= keyWord
    }

    override fun convert(helper: BaseViewHolder?, item: LoveContentInfo?) {
        helper?.let {
            item?.let {
                var str = item.chat_name + "\n"

                val details = item.detail
                details?.let {
                    for ((i, value) in details.withIndex()) {
                        str += LoveContentInfo.getSex(value.ans_sex) + ": " + value.content
                        if (i != details.size - 1) {
                            str += "\n"
                        }
                    }

                }

                helper.getView<MyTextView>(R.id.tv_content).setSpecifiedTextsColor(str, this.mKeyWord, Color.parseColor("#FF0000"))
                helper.addOnClickListener(R.id.tv_look)

            }
        }

    }
}