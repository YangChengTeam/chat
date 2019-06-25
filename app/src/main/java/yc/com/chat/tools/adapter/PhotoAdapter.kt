package yc.com.chat.tools.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import yc.com.chat.R
import yc.com.chat.tools.model.bean.PhotoListBean

class PhotoAdapter(mDatas: List<PhotoListBean.ResultContentBean>?) : BaseQuickAdapter<PhotoListBean.ResultContentBean, BaseViewHolder>(R.layout.small_photo_item, mDatas) {


    override fun convert(helper: BaseViewHolder?, item: PhotoListBean.ResultContentBean?) {
        helper?.let {
            item?.let {
                val photo = item.photo
//                  app:placeholderImage="@mipmap/big3"

                Glide.with(mContext).load(photo).apply(RequestOptions().placeholder(R.mipmap.big3).diskCacheStrategy(DiskCacheStrategy.DATA)).thumbnail(0.1f).into(helper.getView(R.id.iv_small_picture))
//                val parse = Uri.parse(photo)
//                if (photo.contains(".gif")) {
//                    holder.iv_pic.setController((Fresco.newDraweeControllerBuilder().setAutoPlayAnimations(true) as PipelineDraweeControllerBuilder).setUri(parse).build() as PipelineDraweeController)
//                    return
//                }
//                holder.iv_pic.setImageURI(parse)
            }
        }
    }


}