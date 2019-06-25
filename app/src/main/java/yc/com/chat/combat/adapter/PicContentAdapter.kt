package yc.com.chat.combat.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R
import yc.com.chat.base.utils.UserInfoHelper
import yc.com.chat.combat.model.bean.PicContentBean


/**
 *
 * Created by wanglin  on 2019/6/17 18:44.
 */
class PicContentAdapter(list: List<PicContentBean>?) : BaseQuickAdapter<PicContentBean, BaseViewHolder>(R.layout.listview_pic_item, list) {
    //是否支付
    private var isPay = false


    override fun convert(helper: BaseViewHolder?, item: PicContentBean?) {
        helper?.let {
            item?.let {
                val position = helper.adapterPosition
                var isVip = false
                val user = UserInfoHelper.getUser()
                user?.let {
                    isVip = (1 == user.has_vip)
                }

                when {
                    isVip || position < 5 -> {
                        helper.setText(R.id.tv_title, Html.fromHtml(item.joke_name))
                        helper.setBackgroundRes(R.id.iv, R.mipmap.pic)
                        helper.setText(R.id.tv, "目的:提高聊天技巧与情商让你抱得美人归")

                    }
                    else -> {

                        helper.setText(R.id.tv_title, "目的:提高聊天技巧与情商让你抱得美人归")
                        helper.setBackgroundRes(R.id.iv, R.mipmap.suo)
                        helper.setText(R.id.tv, "*******************")
                    }
                }

            }
        }
    }

}