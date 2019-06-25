package yc.com.chat.tools.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R
import yc.com.chat.tools.model.bean.AutographListBean


/**
 *
 * Created by wanglin  on 2019/6/19 11:34.
 */
class AutographListAdapter(mDatas: List<AutographListBean.ResultContentBean>?, mark: String) : BaseQuickAdapter<AutographListBean.ResultContentBean, BaseViewHolder>(R.layout.autograph_list_item, mDatas) {

    private val _mark = mark

    //    app:placeholderImage="@mipmap/big1"
    override fun convert(helper: BaseViewHolder?, item: AutographListBean.ResultContentBean?) {
        helper?.let {
            item?.let {
                val pic = if ("autograph" == _mark) {
                    item.smallPhoto

                } else {
                    //
                    item.bigPhoto
                    //                    val parse = Uri.parse(resultContentBean.getBigPhoto())
                    //                    if (resultContentBean.getBigPhoto().contains(".gif")) {
                    //                        holder.f134iv.setController((Fresco.newDraweeControllerBuilder().setAutoPlayAnimations(true) as PipelineDraweeControllerBuilder).setUri(parse).build() as PipelineDraweeController)
                    //                    } else {
                    //                        holder.f134iv.setImageURI(parse)
                    //                    }
                }
                Glide.with(mContext).load(pic).apply(RequestOptions().placeholder(R.mipmap.big1))
                        .thumbnail(0.1f).into(helper.getView(R.id.iv_picture))
                helper.setText(R.id.tv_title, item.cname)
//                holder.f135tv.setText(resultContentBean.getCname())
            }
        }
    }
}