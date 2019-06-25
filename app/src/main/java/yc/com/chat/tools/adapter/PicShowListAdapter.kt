package yc.com.chat.tools.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import yc.com.chat.R

/**
 *
 * Created by wanglin  on 2019/6/21 14:13.
 */
class PicShowListAdapter(context: Context, num: Int, urls: String) : RecyclerView.Adapter<PicShowListAdapter.MyHolder>() {

    private var mContext: Context = context
    private var mNum = num
    private var mUrls = urls

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.adapter_pic_show_list_item, p0, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return mNum
    }

    override fun onBindViewHolder(holder: MyHolder, i: Int) {
        Glide.with(mContext).load("http://staticfiles.xiazai63.com/" + mUrls + "/" + (i + 1) + ".jpg")
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(R.mipmap.big3)).thumbnail(0.1f).into(holder.iv_small_picture)

        holder.itemView.setOnClickListener { listener?.onItemClick(this@PicShowListAdapter, holder.itemView, i) }
    }


    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iv_small_picture = view.findViewById<ImageView>(R.id.iv_small_picture)
    }


    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(adapter1: PicShowListAdapter, view: View, position: Int)
    }
}