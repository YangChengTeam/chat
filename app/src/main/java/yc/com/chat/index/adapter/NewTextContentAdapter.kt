package yc.com.chat.index.adapter

import android.text.Html
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R
import yc.com.chat.base.utils.UserInfoHelper
import yc.com.chat.index.model.bean.LoveContentInfo


/**
 *
 * Created by wanglin  on 2019/6/17 18:03.
 */
class NewTextContentAdapter(list: List<LoveContentInfo>?) : BaseQuickAdapter<LoveContentInfo, BaseViewHolder>(R.layout.listview_text_content_item, list) {
    private var mIsSearch = false

    fun isSearch(isSearch: Boolean) {
        this.mIsSearch = isSearch
    }


    override fun convert(helper: BaseViewHolder?, item: LoveContentInfo?) {
        helper?.let {
            item?.let {
                var joke_name = item.joke_name
                var joke_content = item.joke_content
                if (joke_name == null || "" == joke_name) {
                    joke_name = "恋爱"
                }
                if (joke_content == null || "" == joke_content) {
                    joke_content = "恋爱"
                }
                var str = item.chat_name + "\n"
                var details = item.details
                if (mIsSearch) {
                    details = item.detail
                }
                details?.let {
                    for ((i, value) in details.withIndex()) {
                        str += LoveContentInfo.getSex(value.ans_sex) + ": " + value.content
                        if (i != details.size - 1) {
                            str += "\n"
                        }
                    }

                }


                val charSequence = Html.fromHtml(joke_name).toString() + "\n" + Html.fromHtml(joke_content)
                val position = helper.adapterPosition

                var isPay = false
                val userInfo = UserInfoHelper.getUser()
                userInfo?.let {
                    isPay = (1 == userInfo.has_vip)
                }
                when {
                    isPay || position < 5 -> {
                        helper.getView<TextView>(R.id.tv_name).setTextIsSelectable(true)
                        helper.setText(R.id.tv_name, str)
                    }
                    else -> {
                        helper.getView<TextView>(R.id.tv_name).setTextIsSelectable(false)
                        helper.setText(R.id.tv_name, "***********\n**********请点击查看*************")
                    }
                }

                helper.addOnClickListener(R.id.tv_look)
            }

        }
    }

}