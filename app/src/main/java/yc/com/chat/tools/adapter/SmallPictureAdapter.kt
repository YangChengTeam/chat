package yc.com.chat.tools.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R
import yc.com.chat.tools.model.bean.PhotoBean


/**
 *
 * Created by wanglin  on 2019/6/19 12:06.
 */
class SmallPictureAdapter(mDatas: List<PhotoBean.ResultContentBean>?) : BaseQuickAdapter<PhotoBean.ResultContentBean, BaseViewHolder>(R.layout.small_picture_item, mDatas) {
    override fun convert(helper: BaseViewHolder?, item: PhotoBean.ResultContentBean?) {

        helper?.let {
            item?.let {
                Glide.with(mContext).load(item.smallPhoto).apply(RequestOptions().placeholder(R.mipmap.big3)).thumbnail(0.1f).into(helper.getView(R.id.iv_small_picture1))
//                holder.iv_pic.setImageURI(Uri.parse((this.mDatas!![position] as ResultContentBean).getSmallPhoto()))
            }
        }
    }

}