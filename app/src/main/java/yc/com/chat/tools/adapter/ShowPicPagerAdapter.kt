package yc.com.chat.tools.adapter

import android.content.Context
import android.net.Uri
import android.support.v4.view.ViewPager
import yc.com.chat.R.id.iv_big_picture
import yc.com.chat.R.layout.big_picture_item
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.view.ViewGroup
import android.support.v4.view.PagerAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import yc.com.chat.R


/**
 *
 * Created by wanglin  on 2019/6/21 12:36.
 */
class ShowPicPagerAdapter(private val mContext: Context, private val num: Int, private val urls: String) : PagerAdapter() {
    /* renamed from: iv */


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun getCount(): Int {
        return if (this.num == 0) 0 else this.num
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val str = "http://staticfiles.xiazai63.com/" + this.urls + "/" + (position + 1) + ".jpg"
        val linearLayout = LayoutInflater.from(this.mContext).inflate(R.layout.big_picture_item, container, false) as LinearLayout
        val iv: ImageView = linearLayout.findViewById(R.id.iv_big_picture) as ImageView
        val parse = Uri.parse(str)
//        if (this.isPay) {
//            this.f139iv!!.setImageURI(parse)
//        } else {
//            showUrlBlur(this.f139iv, str, 3, 6)
//        }
        Glide.with(mContext).load(str).thumbnail(0.1f).into(iv)

        (container as ViewPager).addView(linearLayout)
        return linearLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

//    companion object {
//
//        fun showUrlBlur(draweeView: SimpleDraweeView?, url: String, iterations: Int, blurRadius: Int) {
//            try {
//                draweeView!!.setController(((Fresco.newDraweeControllerBuilder().setOldController(draweeView!!.getController()) as PipelineDraweeControllerBuilder).setImageRequest(ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setPostprocessor(IterativeBoxBlurPostProcessor(6, blurRadius)).build()) as PipelineDraweeControllerBuilder).build())
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }
//    }
}