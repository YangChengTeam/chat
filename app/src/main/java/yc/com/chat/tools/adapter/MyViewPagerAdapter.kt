package yc.com.chat.tools.adapter

import android.support.v4.view.ViewPager
import yc.com.chat.R.id.iv_big_picture
import yc.com.chat.R.layout.big_picture_item
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.view.ViewGroup
import android.app.Activity
import android.net.Uri
import android.support.v4.view.PagerAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import yc.com.chat.R


/**
 *
 * Created by wanglin  on 2019/6/19 14:12.
 */
class MyViewPagerAdapter(private val mDatas: List<String>?, private val mContext: Activity, private val isPay: Boolean) : PagerAdapter() {
    /* renamed from: iv */


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun getCount(): Int {
        return mDatas?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {


        val str = mDatas?.get(position)

        val parse = Uri.parse(str)


        val linearLayout = LayoutInflater.from(mContext).inflate(R.layout.big_picture_item, container, false)
        val ivBigPic: ImageView = linearLayout.findViewById(R.id.iv_big_picture) as ImageView
        if (this.isPay) {
//            this.ivBigPic!!.setImageURI(parse)
            Glide.with(mContext).load(str).apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA)).thumbnail(0.1f).into(ivBigPic)

        } else {
//            showUrlBlur(this.ivBigPic, str, 3, 6)
        }
        container.addView(linearLayout)

        return linearLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

//    companion object {
//
//        fun showUrlBlur(draweeView: SimpleDraweeView?, url: String, iterations: Int, blurRadius: Int) {
//            try {
//                Glide.with()
//
//                draweeView!!.setController(((Fresco.newDraweeControllerBuilder().setOldController(draweeView!!.getController()) as PipelineDraweeControllerBuilder).setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setPostprocessor(IterativeBoxBlurPostProcessor(6, blurRadius)).build()) as PipelineDraweeControllerBuilder).build())
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }
//    }
}